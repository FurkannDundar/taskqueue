package fd.taskqueue.controller;

import fd.taskqueue.dto.TaskRequest;
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
