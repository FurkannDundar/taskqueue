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
        log.info("****FAILURE******");
        log.info("TaskID: {} Başarısız Oldu\n\n", task.getId());


        Task taskObj = taskRepository.findById(task.getId())
                .orElseThrow(() -> new RuntimeException("Task bulunamadı"));
        taskObj.setTaskStatus(TaskStatus.PENDING);
        int retryCount = taskObj.getRetryCount();
        taskObj.setRetryCount(retryCount + 1);
        taskRepository.save(taskObj);

        log.info("TaskID: {} Tekrar TaskQueue'ya Ekleniyor\n\n", task.getId());
        taskQueue.addTaskToQueue(taskObj);
    }

    public void terminateTaskFailure(Task task){
        log.info("****TERMINATED******");
        log.info("TaskID: {} Başarısız Oldu", task.getId());
        log.info("Task Retry: {}, TaskMaxRetry: {}\n\n", task.getRetryCount(), task.getMaxRetry());

        Task taskObj = taskRepository.findById(task.getId())
                .orElseThrow(() -> new NoTaskFoundException("Task Bulunamadı"));
        taskObj.setTaskStatus(TaskStatus.FAILED);
        taskObj.setCompletedAt(LocalDateTime.now());
        taskRepository.save(taskObj);
    }
}

