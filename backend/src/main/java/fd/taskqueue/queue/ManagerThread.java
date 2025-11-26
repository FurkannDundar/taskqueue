package fd.taskqueue.queue;

import fd.taskqueue.entity.Task;
import fd.taskqueue.pool.WorkerPool;
import fd.taskqueue.repository.TaskRepository;
import fd.taskqueue.service.TaskService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagerThread extends Thread{

    private final TaskQueue taskQueue;
    private final WorkerPool workerPool;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @PostConstruct
    public void initialize(){
        this.setName("ManagerThread");
        log.info("{} is başlatıldı. Task işleme süreci başlıyor.", this.getName());
        this.start();
    }

    public void run(){
        while(true){
            Task task = taskQueue.removeTaskFromQueue();
            log.info("TaskID: {} queue'dan çıkarıldı. İşleme alınıyor", task.getId());

            TaskRunner taskRunner = new TaskRunner(task, taskRepository, taskService);

            workerPool.assignTask(taskRunner);
        }
    }
}

