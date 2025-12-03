package fd.taskqueue.pool;

import fd.taskqueue.queue.TaskQueue;
import fd.taskqueue.queue.TaskRunner;
import fd.taskqueue.repository.TaskRepository;
import fd.taskqueue.service.TaskService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkerPool {

    private final TaskQueue taskQueue;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    @Value("${taskqueue.worker.count}")
    private int poolSize;

    // Her bir WorkerThread içinde iş yapılacak.
    // Her bir thread, bir worker threadi yönetecek
    // Threadleri yönetmek için de Thread tipine ihtiyacımız var bu sebeple
    // İkinci liste oluşturuldu
    private final List<WorkerThread> workers = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();

    @PostConstruct
    public void initialize(){
        log.info("=================================================");
        log.info("WorkerPool {} WorkerThread ile başlatılıyor...", poolSize);
        log.info("=================================================");

        startWorkers();

        log.info("=================================================");
        log.info("✅ WorkerPool başarılı şekilde başlatıldı...");
        log.info("=================================================");
    }

    private void startWorkers(){
        for(int i = 0 ; i < poolSize ; i++){
            WorkerThread workerThread = new WorkerThread(
                    i, taskQueue, taskRepository, taskService);
            workers.add(workerThread);

            Thread thread = new Thread(workerThread, "Worker-" + i);
            thread.setDaemon(false);
            thread.start();
            threads.add(thread);

            log.info("Worker-{} başlatıldı", i);
        }
    }
}
