/*
 * $Header: GaussianRandomUtil.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015年8月22日 下午4:53:19
 * $Owner: will
 */
package com.will.toolkit.random;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * RandomUtil 随机工具类
 * @author will
 * @version 1.0.0.0 2015年8月22日 下午4:53:19
 */
public final class RandomUtils {
    private RandomUtils() {};

    /** 随机类 */
    private static final Random random = new Random();

    /** randomMap */
    private static ConcurrentMap<Double, GaussianRandom> randomMap = new ConcurrentHashMap<Double, GaussianRandom>();

    /** randomWeightMap */
    private static ConcurrentMap<double[], GaussianWeightRandom> randomWeightMap = new ConcurrentHashMap<double[], GaussianWeightRandom>();

    /**
     * 获得符合正态分布的随机数
     * @param prob
     * @return
     * @version 1.0.0.0 2015年8月22日 下午4:55:35
     */
    public static GaussianRandom getGaussianRandom(double prob) {
        GaussianRandom random = randomMap.get(prob);
        if (null == random) {
            random = new GaussianRandom(prob);
            GaussianRandom temp = randomMap.putIfAbsent(prob, random);
            random = (null == temp) ? random : temp;
        }
        return random;
    }

    /**
     * 获得符合正态分布的随机数
     * @param probs
     * @return
     * @version 1.0.0.0 2015年8月22日 下午4:55:35
     */
    public static GaussianWeightRandom getGaussianRandom(double[] probs) {
        GaussianWeightRandom random = randomWeightMap.get(probs);
        if (null == random) {
            random = new GaussianWeightRandom(probs);
            GaussianWeightRandom temp = randomWeightMap.putIfAbsent(probs, random);
            random = (null == temp) ? random : temp;
        }
        return random;
    }

    /**
     * 获得符合正态分布的随机数
     * @param probList
     * @return
     * @version 1.0.0.0 2015年8月22日 下午4:55:35
     */
    public static GaussianWeightRandom getGaussianRandom(List<Double> probList) {
        double[] probs = new double[probList.size()];
        for (int i = 0; i < probs.length; i++) {
            probs[i] = probList.get(i);
        }
        GaussianWeightRandom random = randomWeightMap.get(probs);
        if (null == random) {
            random = new GaussianWeightRandom(probs);
            GaussianWeightRandom temp = randomWeightMap.putIfAbsent(probs, random);
            random = (null == temp) ? random : temp;
        }
        return random;
    }

    /**
     * 获取下一个符合正态分布的随机数
     * @param random
     * @param base
     * @param sigma
     * @return
     * @version 1.0.0.0 2015年8月22日 下午3:15:36
     */
    public static double nextGaussianRandom(Random random, double base, double sigma) {
        return base + Math.sqrt(sigma) * random.nextGaussian();
    }

    /**
     * 获取全局随机函数
     * @return
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * 返回[0~num)的随机数
     * @param num
     * @return
     */
    public static int nextInt(int num) {
        return random.nextInt(num);
    }

    /**
     * 返回[min~max)的随机数
     * @param min
     * @param max
     * @return
     */
    public static int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min can't greater than max");
        }
        return min + random.nextInt(max - min);
    }

    /**
     * 随机返回True|False
     * @return
     */
    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    /**
     * 随机返回下一个[0~1)之间的浮点数
     * @return
     */
    public static double nextDouble() {
        return random.nextDouble();
    }

    /**
     * 按照概率获取下一个随机下标
     * @param probs
     * @return
     */
    public static int randomIndex(int[] probs) {
        int totalProb = 0;
        for (int prob : probs) {
            totalProb += prob;
        }
        int randomValue = random.nextInt(totalProb);
        int value = 0;
        for (int i = 0; i < probs.length; i++) {
            value += probs[i];
            if (randomValue < value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 按照概率获取下一个随机下标
     * @param probs
     * @return
     */
    public static int randomIndex(double[] probs) {
        double totalProb = 0;
        for (double prob : probs) {
            totalProb += prob;
        }
        double randomValue = random.nextDouble() * totalProb;
        double value = 0;
        for (int i = 0; i < probs.length; i++) {
            value += probs[i];
            if (randomValue < value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 随机打乱数组
     * @param array
     */
    public static void randomArray(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int index = nextInt(i, array.length);
            if (index != i) {
                swap(array, i, index);
            }
        }
    }

    /**
     * 随机打乱数组
     * @param array
     */
    public static <E> void randomArray(E[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int index = nextInt(i, array.length);
            if (index != i) {
                swap(array, i, index);
            }
        }
    }

    /**
     * 随机打乱List
     * @param list
     */
    public static <E> void randomList(List<E> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int index = nextInt(i, list.size());
            if (index != i) {
                swap(list, i, index);
            }
        }
    }

    /**
     * 交换2个元素
     * @param array
     * @param i
     * @param j
     */
    public static void swap (int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 交换2个元素
     * @param array
     * @param i
     * @param j
     */
    public static <E> void swap (E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 交换2个元素
     * @param list
     * @param i
     * @param j
     */
    public static <E> void swap (List<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static  void main(String[] args) {
        double[] probs = new double[] {0.1, 0.2, 0.3};
        Map<Integer, Integer> map = new HashMap<>();
        int num = 100000;
        for (int i = 0; i < num; i++) {
            int index = randomIndex(probs);
            Integer val = map.get(index);
            if (null == val) {
                map.put(index, 1);
            } else {
                map.put(index, val + 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
