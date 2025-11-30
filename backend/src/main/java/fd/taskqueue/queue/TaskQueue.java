package fd.taskqueue.queue;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.entity.Task;
import fd.taskqueue.exception.NoTaskFoundException;
import fd.taskqueue.pool.WorkerPool;
import fd.taskqueue.repository.TaskRepository;
import fd.taskqueue.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.PriorityBlockingQueue;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskQueue {

    private final TaskRepository taskRepository;
    private final WorkerPool workerPool;

    private PriorityBlockingQueue<Task> taskQueue;
    private TaskService taskService;

    @Value("${taskqueue.engine-capacity}")
    private int queueCapacity;

    private final Comparator<Task> taskComparator;

    @PostConstruct
    public void initializeTaskQueue(){
        log.info("#################################################");
        log.info("TaskQueue {} kapasite ile başlatılıyor", queueCapacity);

        taskQueue = new PriorityBlockingQueue<>(queueCapacity, taskComparator);

        log.info("TaskQueue başlatıldı");
    }

    public void addTaskToQueue(Task newTask){

        Task task = taskRepository.findById(newTask.getId())
                .orElseThrow(() -> new NoTaskFoundException("Task Bulunamadı"));

        if(task.getRetryCount() >= task.getMaxRetry()){
            taskService.terminateTaskFailure(task);
            return;
        }

        log.info("TaskID: {} TaskQueue'ya ekleniyor.", newTask.getId());

        task.setTaskStatus(TaskStatus.QUEUED);
        taskRepository.save(task);
        taskQueue.add(task);
    }

    public Task removeTaskFromQueue(){
        try{
            return taskQueue.take();
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}