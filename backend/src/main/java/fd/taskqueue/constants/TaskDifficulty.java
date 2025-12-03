package fd.taskqueue.constants;

import lombok.Getter;

import java.util.Random;

@Getter
public enum TaskDifficulty {
    EASY(10, 5000),
    MEDIUM(15, 10000 ),
    HARD(30, 15000),
    EXTREME(50, 20000);

    private final Integer failureRate;
    private final Integer durationMs;

    TaskDifficulty(Integer failureRate, Integer durationMs) {
        this.failureRate = failureRate;
        this.durationMs = durationMs;
    }

    public boolean willFail(){
        Random random = new Random();
        int randomValue = random.nextInt(100);

        return randomValue < failureRate;
    }
}