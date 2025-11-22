package fd.taskqueue.constants;

import lombok.Data;
import lombok.Getter;

@Getter
public enum TaskType {
    SEND_EMAIL(TaskDifficulty.MEDIUM),
    PREPARE_REPORT(TaskDifficulty.EXTREME),
    PING_GOOGLE(TaskDifficulty.EASY);

    private final TaskDifficulty difficulty;

    TaskType(TaskDifficulty defaultDifficulty) {
        this.difficulty = defaultDifficulty;
    }
}
