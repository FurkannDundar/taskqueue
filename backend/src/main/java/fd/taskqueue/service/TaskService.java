package fd.taskqueue.service;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.dto.TaskRequest;
import fd.taskqueue.entity.Task;
import fd.taskqueue.exception.NoTaskFoundException;
import fd.taskqueue.queue.TaskQueue;
import fd.taskqueue.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskQueue taskQueue;

    public Task createTask(TaskRequest taskRequest){
        Task task = Task.builder()
        .taskType(taskRequest.getTaskType())
        .taskPriority(taskRequest.getTaskPriority())
                .taskStatus(TaskStatus.PENDING)
        .maxRetry(taskRequest.getMaxRetry())
        .taskDifficulty(taskRequest.getTaskType().getDifficulty()).build();
        return taskRepository.save(task);
    }

    public void failTask(Task task){
        Task taskObj = taskRepository.findById(task.getId())
                .orElseThrow(() -> new NoTaskFoundException("Task Bulunamadı"));


        int retryCount = taskObj.getRetryCount();
        taskObj.setRetryCount(retryCount + 1);

        if(taskObj.getRetryCount() >= taskObj.getMaxRetry()){
            log.error("Task-{} TaskType-{} TaskDifficulty-{} TaskPriority-{} " +
                    "başarısız oldu, artık çalıştırılmayacak",
                    taskObj.getId(), taskObj.getTaskType(),
                    taskObj.getTaskDifficulty(), taskObj.getTaskPriority());
            taskObj.setTaskStatus(TaskStatus.FAILED);
            taskObj.setCompletedAt(LocalDateTime.now());
            taskRepository.save(taskObj);
            return;
        }
        log.error("Task-{} tekrar queue'ya ekleniyor...", taskObj.getId());

        taskObj.setTaskStatus(TaskStatus.QUEUED);
        taskQueue.addTaskToQueue(taskObj);

        taskRepository.save(taskObj);
    }

    public void completeTask(Task task){
        Task taskObj = taskRepository.findById(task.getId())
                .orElseThrow(() -> new NoTaskFoundException("Task Bulunamadı"));
        log.info("Task-{} başarıyla tamamlandı...", taskObj.getId());
        taskObj.setCompletedAt(LocalDateTime.now());
        taskObj.setTaskStatus(TaskStatus.COMPLETED);
        taskRepository.save(taskObj);
    }
}

