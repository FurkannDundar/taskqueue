package fd.taskqueue.controller;

import fd.taskqueue.dto.TaskRequest;
import fd.taskqueue.dto.TaskRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class TaskController {

    @PostMapping
    public void taskController(@RequestBody TaskRequest taskRequest){
        System.out.println(taskRequest);
    }
}
