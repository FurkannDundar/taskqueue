package fd.taskqueue.queue;

import fd.taskqueue.entity.Task;
import fd.taskqueue.pool.WorkerPool;
import fd.taskqueue.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.PriorityBlockingQueue;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskQueue {

    private final TaskRepository taskRepository;
    private final WorkerPool workerPool;

    private PriorityBlockingQueue<Task> taskQueue;

    @Value("${taskqueue.engine-capacity}")
    private int queueCapacity;

    @PostConstruct
    public void initializeTaskQueue(){

    }
}


/*

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskQueue {

    @PostConstruct
    public void init() {
        // A. Sıralama Mantığını Kuruyoruz (Comparator)
        // Kural: Öncelik (Priority) en önemli faktör. Eğer öncelik eşitse, eski tarihli (CreatedAt) öne geçer.
        // Not: Enum sıralaman HIGH, MEDIUM, LOW şeklindeyse naturalOrder yeterli.
        // Değilse aşağıda özel mantık kurabiliriz.
        Comparator<Task> priorityComparator = Comparator
                .comparing(Task::getTaskPriority) // Enum sırasına göre (Dikkat: Enum'da HIGH en üstte olmalı)
                .thenComparing(Task::getCreatedAt);

        this.queue = new PriorityBlockingQueue<>(queueCapacity, priorityComparator);

        // B. Dağıtıcı (Dispatcher) Thread'i Başlat
        // Bu thread, uygulamanın ana thread'ini kilitlememesi için ayrı bir thread olarak başlar.
        Thread dispatcherThread = new Thread(this::dispatchLoop, "Task-Dispatcher");
        dispatcherThread.start();

        log.info("🚀 TaskQueue ve Dispatcher başlatıldı. Kapasite: {}", queueCapacity);
    }

    /**
     * Service katmanı burayı çağırarak işi kuyruğa bırakır.

public void addTask(Task task) {
    boolean added = queue.offer(task);
    if (added) {
        log.info("📥 Task kuyruğa alındı: ID={} Priority={}", task.getId(), task.getTaskPriority());
    } else {
        log.error("❌ Kuyruk dolu! Task reddedildi: ID={}", task.getId());
        // Burada TaskStatus.FAILED yapıp DB'ye güncelleyebilirsin.
    }
}

/**
 * Sonsuz döngüde çalışan ve kuyruğu izleyen metot.

private void dispatchLoop() {
    while (true) {
        try {
            // 1. Kuyruktan iş al (Eğer kuyruk boşsa burada BLOKLANIR ve bekler)
            Task task = queue.take();

            // 2. İşi "Runnable" paketine sar ve WorkerPool'a fırlat
            log.info("📤 Task işlenmek üzere WorkerPool'a gönderiliyor: ID={}", task.getId());
            workerPool.submit(new TaskRunner(task));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Dispatcher durduruldu.");
            break;
        }
    }
}

/**
 * İşin gerçekten yapıldığı yer (Runnable)
 * Worker Thread'ler bu kodu çalıştıracak.

private class TaskRunner implements Runnable {
    private final Task task;

    public TaskRunner(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            // A. İş Başladı
            log.info("⚙️ İşleniyor: ID={}", task.getId());
            updateStatus(TaskStatus.RUNNING);
            task.setStartedAt(LocalDateTime.now());
            taskRepository.save(task);

            // B. Simülasyon (Zorluk seviyesine göre bekle)
            // Enum içindeki metodu kullanarak süreyi belirleyebilirsin.
            // Örn: Thread.sleep(task.getTaskType().getDifficulty().getDurationMs());
            // Şimdilik sabit 2 saniye veriyorum:
            Thread.sleep(2000);

            // C. İş Bitti
            updateStatus(TaskStatus.COMPLETED);
            task.setCompletedAt(LocalDateTime.now());
            taskRepository.save(task);
            log.info("✅ Tamamlandı: ID={}", task.getId());

        } catch (InterruptedException e) {
            log.error("⚠️ İş yarıda kesildi: ID={}", task.getId());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("🔥 Hata oluştu: ID={} Hata: {}", task.getId(), e.getMessage());
            updateStatus(TaskStatus.FAILED);
            task.setFailureReason(e.getMessage());
            taskRepository.save(task);
        }
    }

    private void updateStatus(TaskStatus status) {
        task.setTaskStatus(status);
        // Burada @Version sayesinde optimistic lock hatası alırsan retry mekanizması eklenebilir.
    }
}
}
 */