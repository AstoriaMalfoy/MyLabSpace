package com.astocoding.juc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.List;

/**
 * @author litao34
 * @ClassName CountDownLatch
 * @Description TODO
 * @CreateDate 2022/10/26-10:01
 **/
public class CountDownLatchDemo {

    private static final Integer THREAD_COUNT = 10;

    /**
     * 控制所有的工作线程同时开始
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch singleCountDownLatch  = new CountDownLatch(1);
        CountDownLatch doneCountDownLatch = new CountDownLatch(THREAD_COUNT);


        for (int i=0;i<THREAD_COUNT;i++){
            new Thread(new Worker(singleCountDownLatch,doneCountDownLatch,"WORK_THREAD_" + i)).start();
        }

        doSomethingElse();
        singleCountDownLatch.countDown();
        doSomethingElse();
        doneCountDownLatch.await();
    }

    private static void doSomethingElse() {}

    @Data
    @AllArgsConstructor
    public static class Worker implements Runnable{

        private CountDownLatch singleCountDownLatch;
        private CountDownLatch doneCountDownLath;
        private String threadName;
        private static final String extFormat = "[%s] The thread %s is %s...";
        private static final String STATRT  = "start";
        private static final String FINISH = "finish";

        @Override
        public void run() {
            try {
                singleCountDownLatch.await();
                doWork();
                doneCountDownLath.countDown();
            } catch (InterruptedException e) {
            }

        }

        private void doWork() {}
    }
}
