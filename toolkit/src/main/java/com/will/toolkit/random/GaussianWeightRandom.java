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
 * GaussianWeightRandom 高斯权重随机数
 * @author will
 * @version 1.0.0.0 2015年8月22日 下午3:08:46
 */
public class GaussianWeightRandom {
    private double[] weightProb;
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
     * @param weightProb
     */
    public GaussianWeightRandom(double[] weightProb) {
        this.random = new Random();
        this.weightProb = weightProb;
        this.queue = new PriorityQueue<Tuple<Double,Integer>>(weightProb.length, comparator);
        for (int i = 0; i < weightProb.length; i++) {
            this.queue.add(new Tuple<Double, Integer>(RandomUtils.nextGaussianRandom(random, 1 / weightProb[i], 1 / weightProb[i] / 3), i));
        }
    }
    
    /**
     * 随机
     * @return true 掉落   false 不掉落
     * @version 1.0.0.0 2015年8月22日 下午3:14:13
     */
    public synchronized int random() {
        Tuple<Double, Integer> tuple = this.queue.poll();
        double prob = this.weightProb[tuple.right];
        this.queue.add(new Tuple<Double, Integer>(RandomUtils.nextGaussianRandom(random, 1 / prob, 1 / prob / 3) + tuple.left, tuple.right));
        
        return tuple.right;
    }
    
    public static void main(String[] args) {
        int max = 100000;
        GaussianWeightRandom random = new GaussianWeightRandom(new double[] {0.1,0.4,0.5});
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        for (int i  = 0; i < max; i++) {
            int j = random.random();
            switch (j) {
            case 0:
                num1++;
                break;
            case 1:
                num2++;
                break;
            case 2:
                num3++;
                break;
            default:
                break;
            }
        }
        System.out.println("0.1 " + num1 + "/" + max + " = " + num1 * 1.0 / max);
        System.out.println("0.4 " + num2 + "/" + max + " = " + num2 * 1.0 / max);
        System.out.println("0.5 " + num3 + "/" + max + " = " + num3 * 1.0 / max);
    }
}
