/*
 * $Header: CycleDoubleLinkedList.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 下午02:49:38
 * $Owner: will
 */
package com.will.toolkit.stl;

import java.util.Iterator;

/**
 * CycleDoubleLinkedList
 * @author will
 * @version 1.0.0.0 2012-5-11 下午02:49:38
 */
public class CycleDoubleLinkedList<E> implements Iterable<Node<E>> {
    /** header */
    protected Node<E> header = null;
    
    /** 容量  */
    protected int capacity;
    
    /** 大小 */
    protected int size;
    
    /**
     * 构造函数
     * @param size
     */
    public CycleDoubleLinkedList(int size) {
        this.capacity = size;
        init();
    }
    
    /**
     * 插入到表头位置
     * @param e
     * @version 1.0.0.0 2012-5-11 下午02:58:57
     */
    public synchronized void add(E e) {
        this.header.e = e;
        this.header = this.header.next;
        if (this.size < capacity) {
            this.size++;
        }
    }
    
    /**
     * 获得大小
     * @return
     * @version 1.0.0.0 2012-5-11 下午03:12:00
     */
    public int size() {
        return size;
    }
    
    /**
     * @see Iterable#iterator()
     */
    @Override
    public Iterator<Node<E>> iterator() {
        return new MyIterator(this);
    }

    /**
     * 内部迭代器
     *
     * @author will
     * @version 1.0.0.0 2012-5-11 下午03:13:18
     */
    private class MyIterator implements Iterator<Node<E>> {
        private Node<E> cursor; // 游标
        private int count; // 数量
        private int currentSize; // 大小

        /**
         * @param cycleDoubleLinkedList
         */
        public MyIterator(CycleDoubleLinkedList<E> cycleDoubleLinkedList) {
            cursor = cycleDoubleLinkedList.header;
            currentSize = size;
            count = 1;
        }

        /**
         * @see Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            return count <= currentSize;
        }

        /**
         * @see Iterator#next()
         */
        @Override
        public Node<E> next() {
            cursor = cursor.prev;
            Node<E> temp = cursor;
            count++;
            return temp;
        }

        /**
         * @see Iterator#remove()
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("unsupport this operation");
        }
    }
    
    /**
     * 初始化
     * @version 1.0.0.0 2012-5-11 下午02:54:31
     */
    private void init() {
        for (int i = 0; i < capacity; i++) {
            Node<E> node = getNode(null);
            if (this.header == null) {
                this.header = node;
                this.header.next = this.header;
                this.header.prev = this.header;
            } else {
                add(node);
            }
        }
    }

    /**
     * 添加节点
     * @param node
     * @version 1.0.0.0 2012-5-11 下午02:56:36
     */
    private void add(Node<E> node) {
        node.next = this.header.next;
        node.prev = this.header;
        
        this.header.next.prev = node;
        this.header.next = node;
        
        this.header = this.header.next;
    }
    
    /**
     * 获取节点
     * @param e
     * @return
     * @version 1.0.0.0 2012-5-11 下午02:55:07
     */
    private Node<E> getNode(E e) {
        Node<E> node = new Node<E>(e);
        return node;
    }
    
    public static void main(String[] args) {
        CycleDoubleLinkedList<Integer> list = new CycleDoubleLinkedList<Integer>(10);
//        StopWatch sw = StopWatch.begin();
//        for (int i = 1; i < 100000000; i++) {
//            list.add(i);
//        }
//        sw.stop();
//        System.out.println(sw.getElapsedTime());
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
        list.add(12);
        list.add(13);
        list.add(14);
        list.add(15);
        list.add(16);
        list.add(17);
        
        Iterator<Node<Integer>> it = list.iterator();
        while (it.hasNext()) {
            Node<Integer> node = it.next();
            System.out.println(node.e);
        }
    }

}
