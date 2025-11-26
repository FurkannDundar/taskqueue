package fd.taskqueue.constants;

import lombok.Getter;

@Getter
public enum TaskDifficulty {
    EASY(2, 10000),
    MEDIUM(5, 20000 ),
    HARD(10, 30000),
    EXTREME(50, 50000);

    private Integer failureRate;
    private Integer durationMs;

    TaskDifficulty(Integer failureRate, Integer durationMs) {
        this.failureRate = failureRate;
        this.durationMs = durationMs;
    }
}
