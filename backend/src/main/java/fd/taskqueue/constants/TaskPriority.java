package fd.taskqueue.constants;

import lombok.Getter;

@Getter
public enum TaskPriority {
    LOW(3), MEDIUM(2), HIGH(1), CRITICAL(0);

    private final int value;

    TaskPriority(int value) {
        this.value = value;
    }
}
