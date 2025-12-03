package fd.taskqueue.entity;

import fd.taskqueue.constants.TaskDifficulty;
import fd.taskqueue.constants.TaskPriority;
import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.constants.TaskType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskDifficulty taskDifficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus taskStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskPriority taskPriority;

    @Column(nullable = false)
    private int retryCount;

    @Column(nullable = false)
    private int maxRetry;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime scheduledFor;

    private LocalDateTime completedAt;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.taskStatus = TaskStatus.PENDING;
        this.retryCount = 0;
    }
}