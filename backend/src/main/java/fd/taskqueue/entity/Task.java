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
@Table(name="task")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @PrePersist
    private void onCreate(){
        if(this.createdAt == null){
            this.createdAt = LocalDateTime.now();
        }
        if(this.taskStatus == null){
            this.taskStatus = TaskStatus.PENDING;
        }
        if(this.retryCount == null){
            this.retryCount = 0;
        }
    }
}