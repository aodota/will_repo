/*
 * $Header: RandomUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015年8月22日 下午3:08:46
 * $Owner: will
 */
package com.will.toolkit.random;


import com.will.toolkit.stl.Tuple;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * GaussianRandom 高斯随机数
 * @author will
 * @version 1.0.0.0 2015年8月22日 下午3:08:46
 */
public class GaussianRandom {
    private double prob;
    private double rprob;
    private Random random;
    private PriorityQueue<Tuple<Double, Integer>> queue;
    private static Comparator<Tuple<Double, Integer>> comparator = new Comparator<Tuple<Double,Integer>>() {
        @Override
        public int compare(Tuple<Double, Integer> o1, Tuple<Double, Integer> o2) {
            return o1.left.compareTo(o2.left);
        }
    };
    
    /**
     * 正态分布随机法
     * @param prob
     */
    public GaussianRandom(double prob) {
        this.random = new Random();
        this.prob = prob;
        this.rprob = 1 - this.prob;
        this.queue = new PriorityQueue<Tuple<Double,Integer>>(2, comparator);
        this.queue.add(new Tuple<Double, Integer>(RandomUtils.nextGaussianRandom(random, 1 / this.prob, 1 / this.prob / 3), 0));
        this.queue.add(new Tuple<Double, Integer>(RandomUtils.nextGaussianRandom(random, 1 / this.rprob, 1 / this.rprob / 3), 1));
    }
    
    /**
     * 随机
     * @return true 掉落   false 不掉落
     * @version 1.0.0.0 2015年8月22日 下午3:14:13
     */
    public synchronized boolean random() {
        Tuple<Double, Integer> tuple = this.queue.poll();
        double prob = (tuple.right == 0) ? this.prob : this.rprob;
        this.queue.add(new Tuple<Double, Integer>(RandomUtils.nextGaussianRandom(random, 1 / prob, 1 / prob / 3) + tuple.left, tuple.right));
        if (tuple.right == 0) {
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) throws InterruptedException {
//        int count = 0;
        int max = 1000000;
        final GaussianRandom random = new GaussianRandom(0.99999);
        
        
        for (int k = 0; k < 10; k++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    int count = 0;
                    for (int i  = 0; i < 10000; i++) {
//                        count = 0;
                        for (int j = 0; j < 100; j++) {
                            if (random.random()) {
                                count++;
                            }
                        }
//                        System.out.println(i + ":" + count);
                    }
                    System.out.println(this.getName() + " result is : " + count * 1.0 / 10000 / 100);
                }
            };
            t.start();
            t.join();
        }
        
        long startTime = System.currentTimeMillis();
        for (int i =0; i < max; i++) {
            RandomUtils.getRandom().nextGaussian();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        
    }
}
