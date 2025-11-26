package fd.taskqueue.service;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.dto.TaskRequest;
import fd.taskqueue.entity.Task;
import fd.taskqueue.queue.TaskQueue;
import fd.taskqueue.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}

