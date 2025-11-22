package fd.taskqueue.service;

import fd.taskqueue.constants.TaskStatus;
import fd.taskqueue.dto.TaskRequest;
import fd.taskqueue.entity.Task;
import fd.taskqueue.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public void createTask(TaskRequest taskRequest){
        Task task = Task.builder()
        .taskType(taskRequest.getTaskType())
        .taskPriority(taskRequest.getTaskPriority())
        .maxRetry(taskRequest.getMaxRetry())
        .taskDifficulty(taskRequest.getTaskType().getDifficulty()).build();
        taskRepository.save(task);
    }
}

