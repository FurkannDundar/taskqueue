package fd.taskqueue.queue;

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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}