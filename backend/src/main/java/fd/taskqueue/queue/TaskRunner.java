package fd.taskqueue.queue;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.entity.Task;
import fd.taskqueue.repository.TaskRepository;
import fd.taskqueue.service.TaskService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Data
// Taskların threadlere verilebilmeleri için Runnable olmaları gerekir
public class TaskRunner implements Runnable{

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    private final Task task;

    public TaskRunner(Task task, TaskRepository taskRepository, TaskService taskService) {
        this.taskRepository = taskRepository;
        this.task = task;
        this.taskService = taskService;
    }

    @Override
    public void run() {
        try{

            log.info("İşleniyor... TaskID: {}", task.getId());

            Task taskObj = taskRepository.findById(task.getId())
                    .orElseThrow(() -> new RuntimeException("Task bulunamadı"));
            taskObj.setStartedAt(LocalDateTime.now());
            taskObj.setTaskStatus(TaskStatus.IN_PROGRESS);
            taskRepository.save(taskObj);

            int retry = task.getRetryCount();
            task.setRetryCount(retry + 1);

            int durationMs = task.getTaskDifficulty().getDurationMs();

            Thread.sleep(durationMs);

            int failureRate = task.getTaskDifficulty().getFailureRate();

            Random random = new Random();
            int randomValue = random.nextInt(100);

            if(randomValue < failureRate){
                taskService.failTask(task);
                return;
            }

            log.info("TaskID: {} işlendi", task.getId());

            taskObj.setTaskStatus(TaskStatus.COMPLETED);
            taskObj.setCompletedAt(LocalDateTime.now());
            taskRepository.save(taskObj);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}