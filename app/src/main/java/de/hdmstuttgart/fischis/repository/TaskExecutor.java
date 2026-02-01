package de.hdmstuttgart.fischis.repository;

import java.util.concurrent.Executor;

/**
 * class that manages the threads for DB queries
 */

public class TaskExecutor implements Executor {

    /**
     * this function starts a background thread
     *
     */
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }

}