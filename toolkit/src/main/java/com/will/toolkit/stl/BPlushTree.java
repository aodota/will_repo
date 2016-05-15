/*
 * $Header: BPlushTree.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2014年11月1日 下午2:54:04
 * $Owner: will
 */
package com.will.toolkit.stl;

import java.util.*;

/**
 * BPlushTree B+ Tree实现
 * @author will
 * @version 1.0.0.0 2014年11月1日 下午2:54:04
 */
@SuppressWarnings({"hiding", "unchecked", "rawtypes"})
public class BPlushTree<T extends Comparable<T>, V> {
    /** 默认每个节点最大存储数据数  */
    private static final int DEFAULT_M = 10;
    
    private int M; // 偶数
    private LeafNode head; // 叶子节点的头节点
    private Node root; // 根节点
    
    /**
     * 默认构造函数
     */
    public BPlushTree() {
        this(DEFAULT_M);
    }
    
    /**
     * 构造函数
     * @param m
     */
    public BPlushTree(int m) {
        this.M = m % 2 == 0 ? m : m - 1;
        this.root = new LeafNode();
        this.head = (LeafNode) this.root;
    }
    
    /**
     * 插入元素
     * @param key
     * @param value
     * @version 1.0.0.0 2014-11-30 下午02:27:48
     */
    public void insert(T key, V value) {
        if (key == null) throw new NullPointerException("key can't be null");
        Node<T, V> node = this.root.insert(key, value);
        
        if (node != null) this.root = node;
    }
    
    /**
     * 查找元素
     * @param key
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:28:15
     */
    public V find(T key) {
        return (V) this.root.find(key);
    }
    
    /**
     * 根据自定义比较器查询
     * @param key
     * @param comparator
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:28:56
     */
    public List<V> find(T key, Comparator<T> comparator) {
        return this.root.find(key, comparator);
    }
    
    /**
     * 根据自定义比较器，全表扫描
     * @param matcher
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:29:41
     */
    public List<V> findAll(Matcher<V> matcher) {
        LeafNode<T, V> node = head;
        List<V> resultList = new ArrayList<V>();
        while (null != node) {
            for (int i = 0; i < node.size; i++) {
                if (matcher.match((V) node.values[i])) {
                    resultList.add((V) node.values[i]);
                }
            }
            node = node.next;
        }
        return resultList;
    }
    
    /**
     * 范围查询
     * @param start
     * @param end
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:32:18
     */
    public List<V> rangeFind(T start, T end) {
        if (start.compareTo(end) >= 0) {
            throw new RuntimeException("start can't greater than end");
        }
        
        LeafNode<T, V> startNode = (LeafNode<T, V>) this.root.gteFind(start);
        LeafNode<T, V> endNode = (LeafNode<T, V>) this.root.lteFind(end);
        if (null == startNode) {
            return Collections.EMPTY_LIST;
        } else {
            List<V> resultList = new ArrayList<V>();
            LeafNode<T, V> node = startNode;
            boolean match = false;
            while (null != node && node != endNode) {
                for (int i = 0; i < node.size; i++) {
                    if (!match && start.compareTo((T) node.keys[i]) <= 0) {
                        match = true;
                    }
                    if (match) {
                        resultList.add((V) node.values[i]);
                    }
                }
                node = node.next;
            }
            for (int i = 0; i < endNode.size; i++) {
                if (!match && end.compareTo((T) endNode.keys[i]) <= 0) {
                    match = true;
                }
                if (match) {
                    resultList.add((V) endNode.values[i]);
                }
            }
            
            return resultList;
        }
        
    }
    
    /**
     * 移除指定元素
     * @param key
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:40:11
     */
    public V remove(T key) {
        return (V) this.root.remove(key);
    }
    
    /**
     * 查询树的高度
     * @return
     * @version 1.0.0.0 2014-11-30 下午02:41:35
     */
    public int height() {
        int height = 1;
        Node node = this.root;
        while (!(node instanceof LeafNode)) {
            height++;
            node = ((InternalNode<T, V>)node).pointers[0];
        }
        return height;
    }
    
    /**
     * 打印树
     * 
     * @version 1.0.0.0 2014-11-30 下午02:42:46
     */
    public void print() {
        System.out.println("height: " + height());
        StringBuilder builder = new StringBuilder();
        this.root.print(builder, 1);
        System.out.println(builder.toString());
    }
    
    /** 内部节点  */
    abstract class Node<T extends Comparable<T>, V> {
        /** 父节点  */
        protected Node<T, V> parent;
        /** keys值  */
        protected Object[] keys;
        /** 节点大小  */
        protected int size;
        
        /**
         * 插入数据
         * @param key
         * @param value
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:25
         */
        abstract Node<T, V> insert(T key, V value);
        
        /**
         * 移除数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:40
         */
        abstract V remove(T key);
        
        /**
         * 查找数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:52
         */
        abstract V find(T key);
        
        /**
         * 查询数据
         * @param key
         * @param comparator
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:59:43
         */
        abstract List<V> find(T key, Comparator<T> comparator);
        
        /**
         * >= 查询数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:00:08
         */
        abstract Node<T, V> gteFind(T key);
        
        /**
         * <= 查询数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:00:32
         */
        abstract Node<T, V> lteFind(T key);
        
        /**
         * 打印节点
         * @param builder
         * @param height
         * @version 1.0.0.0 2014年11月1日 下午3:00:57
         */
        abstract void print(StringBuilder builder, int height);
    }
    
    /** 非叶子节点，只存储Keys */
    class InternalNode<T extends Comparable<T>, V> extends Node<T, V> {
        private Node<T, V>[] pointers; // 指向下一级的指针
        
        /**
         * 构造函数
         */
        public InternalNode() {
            this.size = 0;
            this.pointers = new Node[M];
            this.keys = new Object[M];
        }

        /**
         * 插入数据
         * @param key
         * @param value
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:25
         */
        @Override
        public Node<T, V> insert(T key, V value) {
            int i = 1;
            for (; i < this.size; i++) {
                if (key.compareTo((T) this.keys[i]) < 0) break;
            }
            return this.pointers[i - 1].insert(key, value);
        }

        /**
         * 移除数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:40
         */
        @Override
        public V remove(T key) {
            int i = 1;
            for (; i < this.size; i++) {
                if (key.compareTo((T) this.keys[i]) < 0) break;
            }
            return this.pointers[i - 1].remove(key);
        }

        /**
         * 查找数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:52
         */
        @Override
        V find(T key) {
            int i = 1;
            for (; i < this.size; i++) {
                if (key.compareTo((T) this.keys[i]) < 0) break;
            }
            return this.pointers[i - 1].find(key);
        }

        /**
         * 查询数据
         * @param key
         * @param comparator
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:59:43
         */
        @Override
        List<V> find(T key, Comparator<T> comparator) {
            int i = 1;
            List<V> resultList = new ArrayList<V>();
            for (; i < this.size; i++) {
                int cvalue = comparator.compare((T) this.keys[i], key);
                if (cvalue == 0) {
                    resultList.addAll(this.pointers[i - 1].find(key, comparator));
                } else if (cvalue > 0) {
                    break;
                }
            }
            resultList.addAll(this.pointers[i - 1].find(key, comparator));
            return resultList;
        }

        /**
         * >= 查询数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:00:08
         */
        @Override
        public Node<T, V> gteFind(T key) {
            int i = 1;
            for (; i < this.size; i++) {
                if (key.compareTo((T) this.keys[i]) < 0) break;
            }
            return this.pointers[i - 1].gteFind(key);
        }

        /**
         * <= 查询数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:00:32
         */
        @Override
        public Node<T, V> lteFind(T key) {
            int i = 1;
            for (; i < this.size; i++) {
                if (key.compareTo((T) this.keys[i]) < 0) break;
            }
            return this.pointers[i - 1].lteFind(key);
        }

        /**
         * 打印节点
         * @param builder
         * @param height
         * @version 1.0.0.0 2014年11月1日 下午3:00:57
         */
        @Override
        void print(StringBuilder builder, int height) {
            int i = 0;
            builder.append("T").append(height).append("(");
            for (; i < this.size; i++) {
                builder.append(this.keys[i]).append(" ");
            }
            builder.append(") ");

            StringBuilder innerBuilder = new StringBuilder();
            for (i = 0; i < this.size; i++) {
                this.pointers[i].print(innerBuilder, height + 1);
            }
            innerBuilder.append("\n");
            builder.append("\n").append(innerBuilder.toString());
        }

        /**
         * 更新Key值
         * @param newKey
         * @param oldKey
         * @param node
         * @version 1.0.0.0 2014年11月1日 下午3:19:22
         */
        public void update(T newKey, T oldKey, Node<T, V> node) {
            // 查找位置
            int middle = binaryFind(oldKey);
            if (middle == -1) {
                return;
            }

            // 找到位置
            this.keys[middle] = newKey;
            this.pointers[middle] = node;
            if (0 == middle && null != this.parent) {
                ((InternalNode) this.parent).update(newKey, oldKey, this);
            }
        }

        /**
         * 更新指针
         * @param key
         * @param node
         * @version 1.0.0.0 2014年11月1日 下午3:32:02
         */
        public void updatePointer(T key, Node<T, V> node) {
            int middle = binaryFind(key);
            this.pointers[middle] = node;
        }

        /**
         * 插入头部
         * @param key
         * @param value
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void insertIntoHead(Object key, Node value) {
            System.arraycopy(this.keys, 0, this.keys, 1, this.size);
            System.arraycopy(this.pointers, 0, this.pointers, 1, this.size);
            this.keys[0] = key;
            this.pointers[0] = value;
            this.size++;
            value.parent = this;
        }

        /**
         * 插入尾部
         * @param key
         * @param value
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void insertIntoTail(Object key, Node value) {
            this.keys[this.size] = key;
            this.pointers[this.size] = value;
            this.size++;
            value.parent = this;
        }

        /**
         * 从头部删除
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void deleteFromHead() {
            System.arraycopy(this.keys, 1, this.keys, 0, this.size - 1);
            System.arraycopy(this.pointers, 1, this.pointers, 0, this.size - 1);
            this.keys[this.size - 1] = null;
            this.pointers[this.size - 1] = null;
            this.size--;
        }

        /**
         * 从尾部移除
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void deleteFromTail() {
            this.keys[this.size - 1] = null;
            this.pointers[this.size - 1] = null;
            this.size--;
        }

        /**
         * 插入值
         * @param leftKey
         * @param rightKey
         * @param right
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:21:06
         */
        private Node<T, V> insert(T leftKey, Node<T, V> left, T rightKey, Node<T, V> right) {
            if (this.size == 0) {
                // 数组为0时
                this.keys[0] = leftKey;
                this.keys[1] = rightKey;

                this.pointers[0] = left;
                this.pointers[1] = right;

                left.parent = this;
                right.parent = this;
                this.size += 2;
                return this;
            }

            if (this.size >= M) {
                // 满了，需要分裂
                int i = 0;
                for (; i < this.size; i++) {
                    T curKey = (T) this.keys[i];
                    if (curKey.compareTo(rightKey) > 0) break;
                }

                // 需要分裂
                int m = this.size / 2;

                // 分裂成2个节点
                InternalNode<T, V> rightNode = new InternalNode<T, V>();

                rightNode.size = this.size - m;
                System.arraycopy(this.keys, m, rightNode.keys, 0, this.size - m);
                System.arraycopy(this.pointers, m, rightNode.pointers, 0, this.size - m);

                for (int j = 0; j < rightNode.size; j++) {
                    rightNode.pointers[j].parent = rightNode;
                }

                // 清理自己
                for (int j = m; j < this.size; j++) {
                    this.keys[j] = null;
                    this.pointers[j] = null;
                }
                this.size = m;

                // 建立新的父节点
                if (this.parent == null) {
                    this.parent = new InternalNode<T, V>();
                }
                rightNode.parent = this.parent;

                if (i >= m) {
                    rightNode.insert(null, null, rightKey, right);
                } else {
                    this.insert(null, null, rightKey, right);
                }

                return ((InternalNode<T, V>)this.parent).insert((T)this.keys[0], this, (T)rightNode.keys[0], rightNode);
            }

            // 查找插入位置
            int i = 0;
            for (; i < this.size; i++) {
                T curKey = (T) this.keys[i];
                if (curKey.compareTo(rightKey) > 0) break;
            }

            // 插入
            System.arraycopy(this.keys, i, this.keys, i + 1, this.size - i);
            System.arraycopy(this.pointers, i, this.pointers, i + 1, this.size - i);
            this.keys[i] = rightKey;
            this.pointers[i] = right;
            right.parent = this;
            this.size++;

            return null;
        }

        /**
         * 移除节点
         * @param key
         * @version 1.0.0.0 2014年11月1日 下午3:36:05
         */
        public void removePointer(T key) {
            // 找到位置
            int middle = binaryFind(key);

            T headKey = (T) this.keys[0]; // 头节点
            System.arraycopy(this.keys, middle + 1, this.keys, middle, this.size - middle - 1);
            System.arraycopy(this.pointers, middle + 1, this.pointers, middle, this.size - middle - 1);
            this.keys[this.size - 1] = null;
            this.pointers[this.size - 1] = null;
            this.size--;

            int m = M / 2;
            if (this.size < m) {
                if (null == this.parent && this.size < 2) {
                    // 头节点，和字节点合并
                    root = this.pointers[0];
                    this.pointers[0].parent = null;
                } else if (null != this.parent) {
                    InternalNode parent = (InternalNode) this.parent;
                    int index = parent.binaryFind(headKey);
                    InternalNode previous = (InternalNode) ((index > 0) ? parent.pointers[index - 1] : null);
                    InternalNode next = (InternalNode) ((index + 1 < parent.size) ? parent.pointers[index + 1] : null);

                    // 少于m/2个节点
                    if (previous != null && previous.size > m) {
                        // 找前节点补借
                        // 从尾部删除
                        T k = (T) previous.keys[previous.size - 1];
                        Node pointer = previous.pointers[previous.size - 1];
                        previous.deleteFromTail();

                        // 加入头部
                        insertIntoHead(k, pointer);
                        parent.update(k, headKey, this);
                    } else if (next != null && next.size > m) {
                        // 找后面节点借
                        // 从头部删除
                        T k = (T) next.keys[0];
                        Node pointer = next.pointers[0];
                        next.deleteFromHead();
                        parent.update((Comparable) next.keys[0], k, next);

                        // 加入尾部
                        insertIntoTail(k, pointer);
                    } else {
                        if (previous != null && previous.size <= m) {
                            // 同前面节点合并
                            for (int i = 0; i < this.size; i++) {
                                previous.insertIntoTail(this.keys[i], this.pointers[i]);
                            }

                            // 从父节点移除
                            parent.removePointer(headKey);
                        } else if (next != null && next.size <= m) {
                            // 同后面节点合并
                            for (int i = 0; i < next.size; i++) {
                                this.insertIntoTail(next.keys[i], next.pointers[i]);
                            }

                            // 从父节点移除
                            parent.removePointer((T) next.keys[0]);
                        } else {
                            BPlushTree.this.print();
                            throw new RuntimeException("unkonw error");
                        }
                    }
                }
            }
        }

        /**
         * 二分查找
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:11:13
         */
        private final int binaryFind(T key) {
            // 二分查找
            int start = 0;
            int end = this.size;
            int middle = (start + end) / 2;
            while (start < end) {
                if (start == middle) {
                    break;
                }

                T middleKey = (T) this.keys[middle];
                int cvalue = key.compareTo(middleKey);
                if (cvalue == 0) {
                    // 已找到
                    return middle;
                }

                if (cvalue < 0) {
                    end = middle;
                } else {
                    start = middle;
                }
                middle = (start + end) / 2;
            }

            T middleKey = (T) this.keys[middle];
            if (key.compareTo(middleKey) == 0) {
                // 已找到
                return middle;
            }

            // 未找到
            return -1;
        }

    }

    /** 叶子节点， 存储数据 */
    class LeafNode<T extends Comparable<T>, V> extends Node<T, V> {
        // 前节点
        protected LeafNode<T, V> previous;
        // 后节点
        protected LeafNode<T, V> next;
        // 数据
        private Object[] values;

        /**
         * 构造函数
         */
        public LeafNode() {
            this.size = 0;
            this.keys = new Object[M];
            this.values = new Object[M];
            this.parent = null;
        }


        /**
         * 插入数据
         * @param key
         * @param value
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:25
         */
        @Override
        public Node<T, V> insert(T key, V value) {
            if (this.size >= M) {
                // 查找插入位置
                int i = 0;
                for (; i < this.size; i++) {
                    T curKey = (T) this.keys[i];

                    int cvalue = curKey.compareTo(key);
                    if (cvalue == 0) {
                        values[i] = value;
                        return null;
                    }

                    if (cvalue > 0) break;
                }

                // 已满，需要分裂
                int m = this.size / 2;

                // 分裂出一个右节点
                LeafNode<T, V> rightNode = new LeafNode<T, V>();
                rightNode.size = this.size - m;

                // 对右节点赋值，并清理自己
                System.arraycopy(this.keys, m, rightNode.keys, 0, rightNode.size);
                System.arraycopy(this.values, m, rightNode.values, 0, rightNode.size);
                for (int j = m; j < this.size; j++) {
                    this.keys[j] = null;
                    this.values[j] = null;
                }
                this.size = m;

                // 设置链接关系
                if (next != null) {
                    next.previous = rightNode;
                    rightNode.next = next;
                }
                if (previous == null) {
                    head = this;
                }
                rightNode.previous = this;
                this.next = rightNode;

                // 插入节点
                if (i >= m) {
                    rightNode.insert(key, value);
                } else {
                    this.insert(key, value);
                }

                // 设置父节点
                if (this.parent == null) {
                    this.parent = new InternalNode<T, V>();
                }
                rightNode.parent = this.parent;

                // 父节点插入
                return ((InternalNode<T, V>)this.parent).insert((T)this.keys[0], this, (T)rightNode.keys[0], rightNode);
            }

            // 插入到合适的位置
            int i = 0;
            T headKey = (T) this.keys[0];
            for (; i < this.size; i++) {
                T curKey = (T) this.keys[i];

                int cvalue = curKey.compareTo(key);
                if (cvalue == 0) {
                    values[i] = value;
                    return null;
                }

                if (cvalue > 0) break;
            }

            System.arraycopy(this.keys, i, this.keys, i + 1, size - i);
            System.arraycopy(this.values, i, this.values, i + 1, size - i);
            this.keys[i] = key;
            this.values[i] = value;
            this.size++;

            // 更新父节点
            if (i == 0 && null != this.parent) {
                ((InternalNode<T, V>)this.parent).update(key, headKey, this);
            }
            return null;
        }

        /**
         * 移除数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:40
         */
        @Override
        public V remove(T key) {
            if (this.size == 0) return null;

            int middle = binaryFind(key);
            if (middle != -1) {
                // 找到了
                T headKey = (T) this.keys[0];
                V value = (V) this.values[middle];
                System.arraycopy(this.keys, middle + 1, this.keys, middle, this.size - middle - 1);
                System.arraycopy(this.values, middle + 1, this.values, middle, this.size - middle - 1);
                this.keys[this.size - 1] = null;
                this.values[this.size - 1] = null;
                this.size--;

                int m = M / 2;
                if (this.size < m) {
                    // 少于m/2个节点
                    if (this.previous != null && this.previous.size > m && this.previous.parent == this.parent) {
                        // 找前节点补借
                        // 从尾部删除
                        T k = (T) this.previous.keys[this.previous.size - 1];
                        V v = (V) this.previous.values[this.previous.size - 1];
                        this.previous.deleteFromTail();

                        // 加入头部
                        this.insertIntoHead(k, v);
                    } else if (this.next != null && this.next.size > m && this.next.parent == this.parent) {
                        // 从后面节点借
                        // 从头部删除
                        T k = (T) this.next.keys[0];
                        V v = (V) this.next.values[0];
                        this.next.deleteFromHead();
                        if (null != this.next.parent) {
                            ((InternalNode<T, V>)this.next.parent).update((T)this.next.keys[0], k, this.next);
                        }

                        // 加入尾部
                        this.insertIntoTail(k, v);
                    } else {
                        // 都不够借
                        if (this.previous != null && this.previous.size <= m && this.previous.parent == this.parent) {
                            // 同前面节点合并
                            for (int i = 0; i < this.size; i++) {
                                this.previous.insertIntoTail(this.keys[i], this.values[i]);
                            }

                            // 父节点移除
                            ((InternalNode<T, V>)this.parent).removePointer(headKey);

                            // 更新头节点
                            headKey = (T) this.keys[0];

                            // 修正叶子节点链接
                            this.parent = null;
                            LeafNode node = this.next;
                            if (node != null) {
                                node.previous = this.previous;
                                this.previous.next = node;
                            } else {
                                this.previous.next = null;
                            }
                        } else if (this.next != null && this.next.size <= m && this.next.parent == this.parent) {
                            // 同后面节点合并
                            for (int i = 0; i < this.next.size; i++) {
                                this.insertIntoTail(this.next.keys[i], this.next.values[i]);
                            }

                            // 父节点移除
                            ((InternalNode<T, V>)this.parent).removePointer((T) this.next.keys[0]);

                            // 修正叶子节点链接
                            LeafNode node = this.next;
                            if (node.next != null) {
                                node.next.previous = this;
                                this.next = node.next;
                            } else {
                                this.next = null;
                            }
                        } else if (this.parent != null) {
                            System.err.println("BTree ERROR");
                            BPlushTree.this.print();
                            throw new RuntimeException("unknow error");
                        }
                    }
                }

                if (null != this.parent && headKey.compareTo((T) this.keys[0]) != 0) {
                    ((InternalNode<T, V>)this.parent).update((T) this.keys[0], headKey, this);
                }

                return value;
            } else {
                return null;
            }
        }

        /**
         * 查找数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:58:52
         */
        @Override
        public V find(T key) {
            if (this.size == 0) return null;

            int middle = binaryFind(key);
            return (middle != -1) ? (V)this.values[middle] : null;
        }

        /**
         * 查询数据
         * @param key
         * @param comparator
         * @return
         * @version 1.0.0.0 2014年11月1日 下午2:59:43
         */
        @Override
        public List<V> find(T key, Comparator<T> comparator) {
            if (this.size == 0) return Collections.EMPTY_LIST;

            List<V> resultList = new ArrayList<V>(this.size);
            for (int i = 0; i < this.size; i++) {
                int cvalue = comparator.compare((T) this.keys[i], key);
                if(cvalue == 0) {
                    resultList.add((V) this.values[i]);
                } else if (cvalue > 0) {
                    break;
                }
            }
            return resultList;
        }

        /**
         * >= 查询数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:00:08
         */
        @Override
        public Node<T, V> gteFind(T key) {
         // 如果Key比第一个节点大，该Node就包含需要查找的内容
            return (key.compareTo((T) this.keys[0]) >= 0) ? this : null;
        }

        /**
         * <= 查询数据
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:00:32
         */
        @Override
        public Node<T, V> lteFind(T key) {
            return this;
        }

        /**
         * 打印节点
         * @param builder
         * @param height
         * @version 1.0.0.0 2014年11月1日 下午3:00:57
         */
        @Override
        public void print(StringBuilder builder, int height) {
            builder.append("L").append(height).append("(");
            int i = 0; 
            for (; i < this.size; i++) {
                builder.append(this.keys[i]).append(" ");
            }
            builder.append(") ");
        }
        
        /**
         * 插入头部
         * @param key
         * @param value
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void insertIntoHead(Object key, Object value) {
            System.arraycopy(this.keys, 0, this.keys, 1, this.size);
            System.arraycopy(this.values, 0, this.values, 1, this.size);
            this.keys[0] = key;
            this.values[0] = value;
            this.size++;
        }
        
        /**
         * 插入尾部
         * @param key
         * @param value
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void insertIntoTail(Object key, Object value) {
            this.keys[this.size] = key;
            this.values[this.size] = value;
            this.size++;
        }
        
        /**
         * 从头部删除
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void deleteFromHead() {
            System.arraycopy(this.keys, 1, this.keys, 0, this.size - 1);
            System.arraycopy(this.values, 1, this.values, 0, this.size - 1);
            this.keys[this.size - 1] = null;
            this.values[this.size - 1] = null;
            this.size--;
        }
        
        /**
         * 从尾部移除
         * @version 1.0.0.0 2014年11月1日 下午3:33:37
         */
        public void deleteFromTail() {
            this.keys[this.size - 1] = null;
            this.values[this.size - 1] = null;
            this.size--;
        }
        
        /**
         * 二分查找
         * @param key
         * @return
         * @version 1.0.0.0 2014年11月1日 下午3:11:13
         */
        private final int binaryFind(T key) {
            // 二分查找
            int start = 0;
            int end = this.size;
            int middle = (start + end) / 2;
            while (start < end) {
                if (start == middle) {
                    break;
                }
                
                T middleKey = (T) this.keys[middle];
                int cvalue = key.compareTo(middleKey);
                if (cvalue == 0) {
                    // 已找到
                    return middle;
                }
                
                if (cvalue < 0) {
                    end = middle;
                } else {
                    start = middle;
                }
                middle = (start + end) / 2;
            }
            
            T middleKey = (T) this.keys[middle];
            if (key.compareTo(middleKey) == 0) {
                // 已找到
                return middle;
            }
            
            // 未找到
            return -1;
        }
        
    }
    
    public static void main(String[] args) {
        Random random = new Random();
        // 正确性测试
        for (int j = 0; j < 10000; j++) {
            BPlushTree<Integer, String> myTree = new BPlushTree<Integer, String>(4);
            int max = 1000;
            for (int i = 1; i <= max ;i++) {
                myTree.insert(i, String.valueOf(i));
            }
            
            Set<Integer> set = new LinkedHashSet<Integer>();
            for (int i = max; i> 0; i--){
                int tmp = random.nextInt(max) + 1;
                set.add(tmp);
                myTree.remove(tmp);
            }
            
            for (int i = 1; i <= max ;i++) {
                myTree.insert(i, String.valueOf(i));
                set.remove(i);
            }
            
            for (int i = max; i> 0; i--){
                int tmp = random.nextInt(max) + 1;
                set.add(tmp);
                myTree.remove(tmp);
            }
            
            for (int i = 1; i <= max ;i++) {
                myTree.insert(i, String.valueOf(i));
                set.remove(i);
            }
            
            for (int i = max; i> 0; i--){
                int tmp = random.nextInt(max) + 1;
                set.add(tmp);
                myTree.remove(tmp);
            }
            
            for (int k = 1; k <= max; k++) {
                if (!set.contains(k)) {
                    String value = myTree.find(k);
                    if (null == value || !value.equals(String.valueOf(k))) {
                        System.out.println("ERROR");
                    }
                } else {
                    String value = myTree.find(k);
                    if (null != value) {
                        System.out.println("ERROR");
                    }
                }
            }
            
            System.out.println("succ run " + j + " times");
        }
        
//        BPlushTree<Integer, String> myTree = new BPlushTree<Integer, String>(4);
//        myTree.insert(1, "1");
//        myTree.insert(2, "2");
//        myTree.insert(3, "3");
//        myTree.insert(4, "4");
//        myTree.insert(5, "5");
//        myTree.insert(6, "6");
//        myTree.insert(7, "7");
//        myTree.insert(8, "8");
//        myTree.insert(9, "9");
//        myTree.insert(10, "10");
//        
//        myTree.print();
//        
//        myTree.remove(10);
//        myTree.remove(1);
//        myTree.remove(3);
//        myTree.remove(2);
//        myTree.remove(4);
//        myTree.remove(5);
//        
//        
//        myTree.insert(1, "1");
//        myTree.insert(2, "2");
//        myTree.insert(3, "3");
//        
//        myTree.print();
//        
//        myTree.remove(6);
//        myTree.remove(7);
//        
//        myTree.print();
        
        System.out.println("SUCC");
    }
}
