package fd.taskqueue.repository;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus taskStatus);

    long countByStatus(TaskStatus taskStatus);
}


/*

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

// ============= ZAMANLAMA SORGULARI =============

/**
 * Zamanı gelen scheduled task'ları getir
 * (TaskSchedulerService için kritik!)

List<Task> findByStatusAndScheduledForBefore(
        TaskStatus status,
        LocalDateTime dateTime
);

// ============= TİP BAZLI SORGULAR =============

/**
 * Belirli tip ve status'taki task'lar

List<Task> findByTypeAndStatus(TaskType type, TaskStatus status);

/**
 * Belirli tipteki tüm task'lar

List<Task> findByType(TaskType type);

// ============= ÖNCELİK BAZLI SORGULAR =============

/**
 * Yüksek öncelikli pending task'lar

List<Task> findByStatusAndPriorityOrderByCreatedAtAsc(
        TaskStatus status,
        TaskPriority priority
);

/**
 * Önceliğe göre sıralı task'lar

List<Task> findByStatusOrderByPriorityAscCreatedAtAsc(TaskStatus status);

// ============= RETRY YÖNETİMİ =============

/**
 * Retry limitini aşmış task'lar

@Query("SELECT t FROM Task t WHERE t.retryCount >= t.maxRetries AND t.status = :status")
List<Task> findFailedTasksExceededRetries(@Param("status") TaskStatus status);

/**
 * Belirli retry sayısındaki task'lar

List<Task> findByRetryCountGreaterThan(int retryCount);

// ============= İSTATİSTİK SORGULARI =============

/**
 * Bugün oluşturulan task sayısı

@Query("SELECT COUNT(t) FROM Task t WHERE DATE(t.createdAt) = CURRENT_DATE")
long countTodayTasks();

/**
 * Bugün tamamlanan task sayısı

@Query("SELECT COUNT(t) FROM Task t WHERE DATE(t.completedAt) = CURRENT_DATE AND t.status = 'COMPLETED'")
long countTodayCompletedTasks();

/**
 * Status'lere göre gruplama

@Query("SELECT t.status, COUNT(t) FROM Task t GROUP BY t.status")
List<Object[]> countByStatusGrouped();

/**
 * Tiplere göre gruplama

@Query("SELECT t.type, COUNT(t) FROM Task t GROUP BY t.type")
List<Object[]> countByTypeGrouped();

/**
 * Ortalama tamamlanma süresi (saniye)

@Query("SELECT AVG(TIMESTAMPDIFF(SECOND, t.startedAt, t.completedAt)) " +
        "FROM Task t WHERE t.status = 'COMPLETED'")
Double getAverageCompletionTimeInSeconds();

/**
 * Son X günün istatistikleri

@Query("SELECT DATE(t.createdAt) as date, " +
        "COUNT(t) as total, " +
        "SUM(CASE WHEN t.status = 'COMPLETED' THEN 1 ELSE 0 END) as completed, " +
        "SUM(CASE WHEN t.status = 'FAILED' THEN 1 ELSE 0 END) as failed " +
        "FROM Task t " +
        "WHERE t.createdAt >= :startDate " +
        "GROUP BY DATE(t.createdAt) " +
        "ORDER BY DATE(t.createdAt)")
List<Object[]> getDailyStats(@Param("startDate") LocalDateTime startDate);

// ============= TEMİZLİK İŞLEMLERİ =============

/**
 * Eski tamamlanmış task'ları sil
 * (TaskSchedulerService cleanup için)

int deleteByCompletedAtBefore(LocalDateTime dateTime);

/**
 * Belirli status'taki eski task'ları sil

int deleteByStatusAndCreatedAtBefore(
        TaskStatus status,
        LocalDateTime dateTime
);

// ============= ARAMA VE FİLTRELEME =============

/**
 * Payload içinde arama (JSON içinde text search)

@Query("SELECT t FROM Task t WHERE t.payload LIKE %:searchText%")
List<Task> searchInPayload(@Param("searchText") String searchText);

/**
 * Karmaşık filtreleme

@Query("SELECT t FROM Task t WHERE " +
        "(:type IS NULL OR t.type = :type) AND " +
        "(:status IS NULL OR t.status = :status) AND " +
        "(:priority IS NULL OR t.priority = :priority) AND " +
        "(:startDate IS NULL OR t.createdAt >= :startDate) AND " +
        "(:endDate IS NULL OR t.createdAt <= :endDate)")
Page<Task> findWithFilters(
        @Param("type") TaskType type,
        @Param("status") TaskStatus status,
        @Param("priority") TaskPriority priority,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        Pageable pageable
);

// ============= EN YAKIN SCHEDULED TASK =============

/**
 * En yakın zamanda çalışacak scheduled task

Optional<Task> findFirstByStatusOrderByScheduledForAsc(TaskStatus status);

// ============= SON EKLENEN/GÜNCELLENEN =============

/**
 * En son eklenen N task

List<Task> findTop10ByOrderByCreatedAtDesc();

/**
 * En son tamamlanan N task

List<Task> findTop10ByStatusOrderByCompletedAtDesc(TaskStatus status);
}

 */