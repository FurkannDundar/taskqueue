package fd.taskqueue.constants;

import lombok.Getter;

@Getter
public enum TaskType {
    SEND_EMAIL(TaskDifficulty.EASY),
    PREPARE_REPORT(TaskDifficulty.EXTREME),
    SEND_PRIVATE_MESSAGE(TaskDifficulty.EASY),
    FETCH_DATA(TaskDifficulty.MEDIUM),
    GET_LAST_1000_RECORDS(TaskDifficulty.HARD),
    PROCESS_IMAGE(TaskDifficulty.HARD),
    GENERATE_PDF(TaskDifficulty.MEDIUM),
    BACKUP_DATABASE(TaskDifficulty.EXTREME);

    private final TaskDifficulty difficulty;

    TaskType(TaskDifficulty defaultDifficulty) {
        this.difficulty = defaultDifficulty;
    }
}