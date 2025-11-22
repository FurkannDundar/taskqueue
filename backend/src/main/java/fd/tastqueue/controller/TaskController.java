package fd.tastqueue.controller;

import fd.tastqueue.dto.TaskRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@CrossOrigin("*")
public class TaskController {

    @PostMapping
    public void taskController(@RequestBody TaskRequestDTO taskRequestDTO){
        System.out.println(taskRequestDTO);
    }
}
