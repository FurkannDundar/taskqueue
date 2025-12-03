package fd.taskqueue.pool;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.entity.Task;
import fd.taskqueue.exception.NoTaskFoundException;
import fd.taskqueue.queue.TaskQueue;
import fd.taskqueue.repository.TaskRepository;
import fd.taskqueue.service.TaskService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class WorkerThread implements Runnable{

    private boolean running = false;
    private final int id;
    private final TaskQueue taskQueue;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @Override
    public void run() {
        log.info("Worker-{} başlatıldı. Task Bekliyor...", id);
        running = true;
        while(running){
            try{
                Task task = taskQueue.removeTaskFromQueue();

                log.info("Worker-{} task aldı. TaskID: {}", id, task.getId());

                processTask(task);
            }catch (InterruptedException exception){
                log.warn("Worker-{} interrupt edildi...", id);
                Thread.currentThread().interrupt();
            }
        }
        log.info("Worker-{} çalışmayı durdurdu...", id);
    }

    public void processTask(Task task){
        try{
            log.info("TaskID: {}, TaskType: {}, TaskPriority: {}," +
                    "TaskDifficulty: {} işleniyor...", task.getId(),
                task.getTaskType(), task.getTaskPriority(), task.getTaskDifficulty());

            Task taskObj = taskRepository.findById(task.getId())
                    .orElseThrow(() -> new NoTaskFoundException("Task Bulunamadı"));

            taskObj.setStartedAt(LocalDateTime.now());
            taskObj.setTaskStatus(TaskStatus.IN_PROGRESS);

            taskRepository.save(taskObj);

            boolean willFail = task.getTaskDifficulty().willFail();

            if(willFail) {
                log.error("Worker-{} Task-{} için başarısız oldu...", id, task.getId());

                taskService.failTask(task);
            }
            else {
                int duration = task.getTaskDifficulty().getDurationMs();
                try{
                    log.info("Worker-{} Task-{} id'li taskı çalıştırmaya başladı...",
                            id, task.getId());
                    Thread.sleep(duration);
                }catch (InterruptedException exception){
                    log.error("Worker-{} interrupt edildi...", id);
                    taskService.failTask(task);
                    return;
                }
                log.info("Worker-{} Task-{} id'li taskı tamamladı...", id, task.getId());
                taskService.completeTask(task);
            }
        }catch (Exception exception){
            taskService.failTask(task);
        }
    }
}
