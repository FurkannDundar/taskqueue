package fd.taskqueue.queue;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueueReporter extends Thread {

    private final TaskQueue taskQueue;

    @PostConstruct
    public void init(){
        this.setName("QueueReporter");
        this.start();
    }

    public void run(){
        log.info("QueueReporter başlatıldı. Her 20 saniyede bir rapor verilecek.");
        while(true){
            try {
                taskQueue.taskQueueReport();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}