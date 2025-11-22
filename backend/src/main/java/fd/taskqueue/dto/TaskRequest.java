package fd.taskqueue.dto;

import fd.taskqueue.constants.TaskPriority;
import fd.taskqueue.constants.TaskType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {

    @NotNull(message = "Task Tipi Boş Olamaz")
    private TaskType taskType;

    @NotNull(message = "Task Önceliği Boş Olamaz")
    private TaskPriority taskPriority;

    private Integer maxRetry;
}

