package fd.taskqueue.comparator;

import fd.taskqueue.entity.Task;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component
public class TaskPriorityComparator implements Comparator<Task> {

    @Override
    public int compare(Task task1, Task task2) {

        int result = Integer.compare(task1.getTaskPriority().getValue(),
                task2.getTaskPriority().getValue());
        if(result == 0){
            result = task1.getCreatedAt().compareTo(task2.getCreatedAt());
        }
        return result;
    }
}
