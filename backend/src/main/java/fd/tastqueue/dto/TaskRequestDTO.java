package fd.tastqueue.dto;

public class TaskRequestDTO {

    private String maxRetry;
    private String priority;
    private String task;

    @Override
    public String toString() {
        return "TaskRequestDTO{" +
                "maxRetry='" + maxRetry + '\'' +
                ", priority='" + priority + '\'' +
                ", task='" + task + '\'' +
                '}';
    }

    public String getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(String maxRetry) {
        this.maxRetry = maxRetry;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
