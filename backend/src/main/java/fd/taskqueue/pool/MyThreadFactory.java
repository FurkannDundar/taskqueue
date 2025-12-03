package fd.taskqueue.pool;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

    private int counter = 1;

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }
}


/*
        return new ThreadFactory() {
    private int counter = 1;

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("TaskWorker-" + counter++);
        thread.setDaemon(false);
        return thread;
    }
};
 */