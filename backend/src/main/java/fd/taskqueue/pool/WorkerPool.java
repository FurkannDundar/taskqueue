package fd.taskqueue.pool;

import fd.taskqueue.entity.Task;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkerPool {

    private ExecutorService executorService;

    @Value("${taskqueue.worker-count}")
    private Integer poolSize;

    @PostConstruct
    public void initializeWorkerPool(){
        log.info("#####################################################");
        log.info("WorkerPool başlatılıyor. Toplam işçi thread sayısı: {}", poolSize);

        this.executorService = Executors.newFixedThreadPool(poolSize);

        log.info("WorkerPool {} adet thread ile kullanıma hazır.", poolSize);
    }
}
