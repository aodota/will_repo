/*
 * $Header: DoubleLinkedList.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-26 下午1:55:58
 * $Owner: will
 */
package com.will.toolkit.stl;


/**
 * DoubleLinkedList 双向链表
 * @author will
 * @version 1.0.0.0 2012-5-26 下午1:55:58
 */
public class DoubleLinkedList<E> {
    /** 头结点 */
    public Node<E> header;
    /** 尾结点  */
    public Node<E> tail;
    /** 大小 */
    private int size;
    
    
    /**
     * 构造函数
     */
    public DoubleLinkedList() {
        this.header = getNode(null);
        this.tail = getNode(null);
        
        this.header.next = this.tail;
        this.tail.prev = this.header;
    }
    
    /**
     * 添加元素到双向链表， 添加到列表的尾部
     * @param e
     * @return
     * @version 1.0.0.0 2012-5-26 下午2:00:25
     */
    public synchronized Node<E> add(E e) {
        Node<E> node = getNode(e);
    
        node.prev = this.tail.prev;
        node.next = this.tail;
        this.tail.prev.next = node;
        this.tail.prev = node;
       
        
        size++;
        
        return node;
    }
    
    /**
     * 移除队尾的第一个元素
     * @return
     * @version 1.0.0.0 2012-5-26 下午2:02:15
     */
    public synchronized Node<E> pop() {
        if (this.tail.prev == this.header) {
            return null;
        }
        
        Node<E> node = this.tail.prev;
        node.prev.next = this.tail;
        this.tail.prev = node.prev;
        
        size--;
        
        return node;
    }
    
    /**
     * 在制定元素之前添加
     * @param e
     * @param node
     * @return
     * @version 1.0.0.0 2012-5-26 下午2:06:44
     */
    public synchronized Node<E> addBefore(E e, Node<E> node) {
        Node<E> temp = getNode(e);
        
        temp.prev = node.prev;
        temp.next = node;
        node.prev.next = temp;
        node.prev = temp;
        
        
        size++;
        
        return temp;
    }
    
    /**
     * 在制定元素之后添加
     * @param e
     * @param node
     * @return
     * @version 1.0.0.0 2012-5-26 下午2:06:44
     */
    public synchronized Node<E> add(E e, Node<E> node) {
        Node<E> temp = getNode(e);
        
        temp.next = node.next;
        temp.prev = node;
        node.next.prev = temp;
        node.next = temp;
        
        size++;
        
        return temp;
    }
    
    /**
     * 移除元素
     * @param node
     * @version 1.0.0.0 2012-5-26 下午2:35:05
     */
    public synchronized void remove(Node<E> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
    }
    
    
    /**
     * @see Iterable#iterator()
     */
    public DoubleIterator<Node<E>> iterator(boolean reverse) {
        return new MyIterator(reverse);
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
    
    /**
     * 内部迭代器
     * 
     * @author will
     * @version 1.0.0.0 2012-5-11 下午03:13:18
     */
    public class MyIterator implements DoubleIterator<Node<E>> {
        private boolean reverse; // 是否是反向遍历
        private Node<E> node; // 当前遍历到的结点
        
        /**
         * MyIterator
         * @param reverse
         */
        public MyIterator(boolean reverse) {
            if (reverse) {
                this.node = tail;
            } else {
                this.node = header;
            }
            this.reverse = reverse;
        }

        /**
         * @see java.util.Iterator#hasNext()
         */
        public boolean hasNext() {
            if (reverse) {
                throw new UnsupportedOperationException("doesn't support");
            }
            return this.node.next != tail && this.node.next != null;
        }
        
        /**
         * 是否有上一个元素
         * @return
         * @version 1.0.0.0 2012-5-26 下午2:19:24
         */
        public boolean hasPrev() {
            if (!reverse) {
                throw new UnsupportedOperationException("doesn't support");
            }
            return this.node.prev != header && this.node.prev != null;
        }

        /**
         * @see java.util.Iterator#next()
         */
        public Node<E> next() {
            this.node = this.node.next;
            Node<E> node = this.node;
            return node;
        }
        
        /**
         * 前一个元素
         * @return
         * @version 1.0.0.0 2012-5-26 下午2:20:03
         */
        public Node<E> prev() {
            this.node = this.node.prev;
            Node<E> node = this.node;
            return node;
        }

        /**
         * @see java.util.Iterator#remove()
         */
        public void remove() {
            throw new UnsupportedOperationException("unsupport this operation");
        }
    }
    
    public static void main(String[] args) {
        DoubleLinkedList<Integer> node = new DoubleLinkedList<Integer>();
        node.add(1);
        Node<Integer> n = node.add(2);
        node.remove(n);
        node.add(3);
        node.add(4);
        
        DoubleIterator<Node<Integer>>  it = node.iterator(true);
        while (it.hasPrev()) {
            System.out.println(it.prev().e);
        }
    }

    /**
     * 获得大小
     * @return
     * @version 1.0.0.0 2012-5-26 下午3:29:41
     */
    public int size() {
        return size;
    }

}
