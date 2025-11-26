package fd.taskqueue.controller;

import fd.taskqueue.dto.TaskRequest;
import fd.taskqueue.entity.Task;
import fd.taskqueue.queue.TaskQueue;
import fd.taskqueue.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@CrossOrigin("*")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    private final TaskQueue taskQueue;

    @PostMapping("/create")
    public void taskController(@RequestBody TaskRequest taskRequest){
        log.info("#####################################################");
        log.info("Task Request Arrived: {}", taskRequest);
        log.info("#####################################################");

        Task task = taskService.createTask(taskRequest);

        log.info("Task Created. TaskID: {}", task.getId());

        // Queue'nun dolu olma durumu kontrol edilecek
        taskQueue.addTaskToQueue(task);
    }
}
