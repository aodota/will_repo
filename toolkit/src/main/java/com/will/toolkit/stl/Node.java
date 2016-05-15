/*
 * $Header: Node.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-5-11 下午02:50:59
 * $Owner: will
 */
package com.will.toolkit.stl;

/**
 * 节点
 * @author will
 * @version 1.0.0.0 2012-5-11 下午02:50:59
 */
public class Node<E> {
    public E e;
    public Node<E> prev;
    public Node<E> next;
    
    /**
     * 构造函数
     */
    public Node() {
    }

    /**
     * 构造函数
     * @param e
     * @param prev
     * @param next
     */
    public Node(E e, Node<E> prev, Node<E> next) {
        this.e = e;
        this.prev = prev;
        this.next = next;
    }
    
    /**
     * 构造函数
     * @param e
     */
    public Node(E e) {
        this.e = e;
        this.prev = null;
        this.next = null;
    }
    
}
