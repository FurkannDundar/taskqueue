package fd.tastqueue.entity;

import fd.tastqueue.constants.TaskDifficulty;
import fd.tastqueue.constants.TaskPriority;
import fd.tastqueue.constants.TaskStatus;
import fd.tastqueue.constants.TaskType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="task")
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    private Integer maxRetry;

    @Column(length=1000)
    private String failureReason;

    @Column(nullable = false)
    private Integer retryCount;

    @Column(nullable=false)
    private LocalDateTime createdAt;

    private LocalDateTime scheduledFor;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;
}
