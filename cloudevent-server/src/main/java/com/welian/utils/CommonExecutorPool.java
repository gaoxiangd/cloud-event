package com.welian.utils;

import org.sean.framework.util.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


/**
 * @author Tomas
 */
public class CommonExecutorPool {

    private static final Logger logger = Logger.newInstance(CommonExecutorPool.class);

    public static int ThreadCount = 50;
    public final static ExecutorService workerPool = Executors.newFixedThreadPool(CommonExecutorPool.ThreadCount);
    public final static ExecutorService pushThreadPool = Executors.newFixedThreadPool(10);
    public final static ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);

    public CommonExecutorPool() {

    }

    public static void execute(Runnable message) {
        try {
            workerPool.execute(message);
        } catch (Exception e) {
            logger.printStackTrace(e);
        }
    }

    public static void shutdown() {
        try {
            workerPool.shutdown();
        } catch (Exception e) {
            logger.printStackTrace(e);
        } finally {
            try {
                pushThreadPool.shutdown();
            } catch (Exception e) {
                logger.printStackTrace(e);
            } finally {
                try {
                    scheduledPool.shutdown();
                } catch (Exception e) {
                    logger.printStackTrace(e);
                }
            }
        }
    }
}
