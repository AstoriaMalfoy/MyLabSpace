package com.astocoding.juc;

import lombok.*;

import java.util.concurrent.Semaphore;

/**
 * @author litao34
 * @ClassName SemaphoreDemo
 * @Description TODO
 * @CreateDate 2022/10/26-17:04
 **/
public class SemaphoreDemo {

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    private class Resources{
        private Long data;
        private String message;
        private volatile Boolean isRunning;
    }

    public static void main(String[] args) {
//        new SemaphoreDemo().simpleMutex();
        new SemaphoreDemo().simpleSync();
    }





    /**
     *  经典信号量问题：简单互斥问题
     *  信号量初始值为1，在请求访问临界资源时执行P操作，在完成对临界资源的访问之后执行V操作
     *  相当于只有一把锁
     */
    public void simpleMutex(){
        Semaphore semaphore = new Semaphore(1);
        Resources resources = new Resources(System.currentTimeMillis(),"",true);
        Boolean isRunning = true;

        new Thread(new SimpleMutexRunnabel(semaphore,resources),"ThreadA").start();
        new Thread(new SimpleMutexRunnabel(semaphore,resources),"ThreadB").start();

        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        resources.setIsRunning(false);

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SimpleMutexRunnabel implements Runnable{
        
        private Semaphore semaphore;
        private Resources resources;

        @Override
        public void run() {
            Integer cycliCount = 0;
            while (resources.isRunning){
                try {
                    semaphore.acquire();
                    resources.setData(System.currentTimeMillis());
                    String threadName = Thread.currentThread().getName();
                    resources.setMessage("the thread " + threadName + " write the resources.data " + resources.getData() + " cycliCount " + cycliCount++);
                    System.out.println(resources);
                    semaphore.release();
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }

    }


    /**
     * 经典信号量问题：简单同步问题
     * 信号量初始值为0，在前置线程完成之后对信号量执行V操作，
     * 而后置线程在对线程操作之前执行P操作。
     */
    @SneakyThrows
    public void simpleSync()  {
        Semaphore semaphore = new Semaphore(0);
        Resources resources = new Resources(System.currentTimeMillis(),"",true);

        Thread firstThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("the first thread is start...");
                resources.setData(System.currentTimeMillis());
                resources.setMessage("First Thread build message");
                resources.setIsRunning(true);
                semaphore.release();
            }
        });

        Thread secondThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("the second thread is start...");
                    semaphore.acquire();
                    System.out.println("the second thread get resouces " + resources);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        secondThread.start();
        Thread.sleep(1000);
        firstThread.start();
        Thread.sleep(1000);

    }

    public void oneProducerOneConsumersOneBuffer(){

    }

}
