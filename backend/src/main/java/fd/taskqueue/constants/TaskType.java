package fd.taskqueue.constants;

import lombok.Getter;

@Getter
public enum TaskType {
    SEND_EMAIL(TaskDifficulty.MEDIUM),
    PREPARE_REPORT(TaskDifficulty.EXTREME),
    SEND_PRIVATE_MESSAGE(TaskDifficulty.EASY),
    FETCH_DATA(TaskDifficulty.MEDIUM),
    GET_LAST_1000_RECORDS(TaskDifficulty.HARD);

    private final TaskDifficulty difficulty;

    TaskType(TaskDifficulty defaultDifficulty) {
        this.difficulty = defaultDifficulty;
    }
}