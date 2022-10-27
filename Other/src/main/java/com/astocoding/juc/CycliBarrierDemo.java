package com.astocoding.juc;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.List;

/**
 * @author litao34
 * @ClassName CycliBarrierDemo
 * @Description TODO
 * @CreateDate 2022/10/26-14:57
 **/
public class CycliBarrierDemo {

    private Integer[][] data;
    private Integer N;
    private Boolean isDone = true;

    private List<Thread> threads = new ArrayList<>();
    private List<Worker> works = new ArrayList<>();


    public static void main(String[] args) {
        Integer[][] data = new Integer[][]{
                {1,2,3,4,5,6,7,8,9},
                {2,3,4,5,6,7,8,9,10},
                {3,4,5,6,7,8,9,10,11},
                {4,5,6,7,8,9,10,11,12},
                {5,6,7,8,9,10,11,12,13},
        };
        new CycliBarrierDemo().slover(data);
    }

    private void slover(Integer[][] data){
        this.data = data;
        this.N = data.length;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N,new Thread(new BAction()));
        for (int i = 0 ; i < N ; i++){
            Thread currentThread = null;
            Worker worker = null;
            threads.add(currentThread =  new Thread(worker = new Worker(i,cyclicBarrier)));
            works.add(worker);
            currentThread.start();
        }
        for (int i = 0 ; i < N ; i++){
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private boolean isDone(){
        return isDone;
    }

    class BAction implements Runnable{
        @Override
        public void run() {
            int count = 0 ;
            for (Worker work : works) {
                count+=work.currentValue;
            }
            System.out.println("one cyclic sum = " + count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (works.get(0).currentRow == 9){
                isDone = false;
            }
        }
    }

    @Data
    @Getter
    class Worker implements Runnable{

        private Integer index;
        private CyclicBarrier cyclicBarrier;
        private Integer currentRow = 0;

        private Integer currentValue = 0;

        public Worker(Integer index, CyclicBarrier cyclicBarrier) {
            this.index = index;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            while(isDone()){
                currentValue = data[index][currentRow];
                System.out.println("get the value " + currentValue);
                this.currentRow++;
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }





}
