package fd.taskqueue.pool;

import fd.taskqueue.entity.Task;
import fd.taskqueue.queue.TaskRunner;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkerPool {

    private ThreadPoolExecutor executorService;

    @Value("${taskqueue.worker-count}")
    private Integer poolSize;

    @PostConstruct
    public void initializeWorkerPool(){
        log.info("#####################################################");
        log.info("WorkerPool başlatılıyor. Toplam işçi thread sayısı: {}", poolSize);

        this.executorService = new ThreadPoolExecutor(poolSize, poolSize,
                0L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

        log.info("WorkerPool {} adet thread ile kullanıma hazır.", poolSize);
    }

    // Thread'ler Task değil TaskRunner çalıştırabilir
    public boolean assignTask(TaskRunner taskRunner) {
        log.info("TaskID: {} çalıştırılmak üzere worker'a iletiliyor", taskRunner.getTask().getId());

        if (executorService.getActiveCount() < poolSize) {
            executorService.submit(taskRunner);
            return true;
        }
        log.info("TaskID: {} çalıştırılamadı. Worker Havuzu Dolu.", taskRunner.getTask().getId());
        return false;
    }
}
