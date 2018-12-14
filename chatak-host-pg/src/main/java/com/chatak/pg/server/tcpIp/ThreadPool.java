package com.chatak.pg.server.tcpIp;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * @Version : 1.0 This class is implements a simple thread pool that operates on
 *          a blocking work queue. Each thread blocks on the queue while it's
 *          empty. When some work is available the work is removed from the
 *          front of the queue and executed. The number of threads residing in
 *          the pool is configured by the numberThreads argument in the
 *          constructor.
 */
@SuppressWarnings("unused")
public class ThreadPool {

  private final int numberThreads;

  // array of queue work threads;
  private final PoolWorker[] threads;

  // queue of work to be performed
  private final ArrayList<Runnable> queue;

  // closed flag
  private boolean isClosed = false;

  // isDaemon flag
  private boolean isDaemon = false;

  private Logger logger = Logger.getLogger(ThreadPool.class);;

  /**
   * Parmaterized constructor
   * 
   * @param numberThreads
   *          - number of threads in the pool
   */
  public ThreadPool(int numberThreads, boolean isDaemon) {
    // save away the number of threads and isDaemon flag in the pool
    this.numberThreads = numberThreads;
    this.isDaemon = isDaemon;

    // create work queue and thread array
    queue = new ArrayList<Runnable>();
    threads = new PoolWorker[numberThreads];

    // create and start up threads
    for(int i = 0; i < numberThreads; i++) {
      threads[i] = new PoolWorker();
      threads[i].setDaemon(isDaemon);
      threads[i].start();
    }
  }

  /**
   * Execute some work
   * 
   * @param r
   *          - Runnable interface instance
   */
  public void run(Runnable work) {
    // synchronize when operation on the work queue
    synchronized(queue) {
      // add to the end of the queue
      queue.add(work);

      // notify that there is some work to be performed
      queue.notify();
    }
  }

  /**
   * Close down the pool
   */
  public void close() {
    isClosed = true;
    queue.notify();
  }

  /**
   * PoolWorker thread class
   */
  private class PoolWorker extends Thread {
    /**
     * Overide super class run()
     */
    public void run() {
      Runnable work;

      // do until closed
      while(!isClosed) {
        synchronized(queue) {
          // block on empty work queue
          while(queue.isEmpty()) {
            try {
              // wait to be notified
              queue.wait();

              // if closed return as soon as notified
              if(isClosed)
                return;
            }
            catch(InterruptedException ignored) {
            }
          }

          // get some work from the front queue
          work = queue.remove(0);
        }

        // if we don't catch RuntimeException,
        // the queue could leak threads
        try {
          // do the work
          work.run();
        }
        catch(RuntimeException e) {
          e.printStackTrace();
          logger.error("caught thread runtime error: " + e.getMessage(), e);
        }
      }
    }
  }
}
