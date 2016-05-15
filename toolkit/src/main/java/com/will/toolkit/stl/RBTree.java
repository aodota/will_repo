/*
 * $Header: RBTree.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2013-1-17 下午03:14:53
 * $Owner: will
 */
package com.will.toolkit.stl;

import java.util.ArrayList;
import java.util.List;

/**
 * RBTree红黑树
 * 红黑树的规则：
 * 性质 1：每个节点要么是红色，要么是黑色。
 * 性质 2：根节点永远是黑色的。
 * 性质 3：所有的叶节点都是空节点（即 null），并且是黑色的。
 * 性质 4：红色节点的父、左子、右子节点都是黑色。
 * 性质 5：从任一节点到其子树中每个叶子节点的路径都包含相同数量的黑色节点。
 * @author will
 * @version 1.0.0.0 2013-1-17 下午03:14:53
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RBTree<T extends Comparable<T>> {
    /** 颜色 */
    private enum Color {RED, BLACK};
    
    private Node<T> root;
    
    public RBTree() {
    }
    
    /**
     * 添加一个节点
     * @param e
     * @version 1.0.0.0 2013-1-17 下午03:20:29
     */
    public void add(T e) {
        Node<T> node = getNode(e);
        
        // 插入到树中
        Node<T> current = root;
        if (null == root) {
            root = node;
            current = node;
        } else {
            while (true) {
                if (current.e.compareTo(e) > 0) {
                    if (current.left == null) {
                        current.left = node;
                        node.parent = current;
                        current = node;
                        break;
                    }
                    current = current.left;
                } else {
                    // 大于当前节点，在右侧
                    if (current.right == null) {
                        current.right = node;
                        node.parent = current;
                        current = node;
                        break;
                    }
                    current = current.right;
                }
            }
        }
        
        fixTreeAfterInsert(current);
    }
    
    /**
     * 插入之后调整红黑树
     * @param n
     * @version 1.0.0.0 2013-1-17 下午03:34:35
     */
    private void fixTreeAfterInsert(Node<T> n) {
        // 情形 1：新节点 N 是树的根节点，没有父节点
        if (n == root) {
            n.color = Color.BLACK;
            return;
        }
        
        // 情形 2：新节点的父节点 P 是黑色
        Node<T> p = n.parent;
        if (p.color == Color.BLACK) {
            return;
        }
        
        // 情形 3：如果父节点 P 和父节点的兄弟节点 U都是红色
        Node<T> u = uncle(n);
        Node<T> g = grandParent(n);
        if (u != null && u.color == Color.RED) {
            // 修改父亲节点为黑色
            // 修改叔叔节点为黑色
            // 修改祖父节点为红色
            p.color = Color.BLACK;
            u.color = Color.BLACK;
            g.color = Color.RED;
            fixTreeAfterInsert(g);
            return;
        } else if (u == null || u.color == Color.BLACK) {
            // 情形4: 父节点P是红色，叔叔节点U是黑色或NIL; 
            if (g != null) {
                if (n == p.right && p == g.left) {
                    rotateLeft(p);
                    n = n.left;
                    fixTreeAfterInsert(n);
                    return;
                } else if (n == p.left && p == g.right) {
                    rotateRight(p);
                    n = n.right;
                    fixTreeAfterInsert(n);
                    return;
                } 
                if (n == p.right && p == g.right) {
                    p.color = Color.BLACK;
                    g.color =  Color.RED;
                    rotateLeft(g);
                    if (g == root) {
                        root = p;
                    }
                } else if (n == p.left && p == g.left) {
                    p.color = Color.BLACK;
                    g.color =  Color.RED;
                    rotateRight(g);
                    if (g == root) {
                        root = p;
                    }
                } 
            }
        }
    }
    
    /**
     * 右旋操作
     * @param parent
     * @version 1.0.0.0 2013-1-17 下午04:09:56
     */
    private void rotateRight(Node<T> node) {
        Node<T> left = node.left;
        node.left = left.right;
        if (left.right != null) {
            left.right.parent = node;
        }
        
        left.right = node;
        Node<T> p = node.parent;
        if (null != p) {
            left.parent = p;
            if (p.left == node) {
                p.left = left;
            } else {
                p.right = left;
            }
        } else {
            root = left;
        }
        node.parent = left;
    }

    /**
     * 左旋操作
     * @param parent
     * @version 1.0.0.0 2013-1-17 下午04:06:16
     */
    private void rotateLeft(Node<T> node) {
        Node<T> right = node.right;
        node.right = right.left;
        if (right.left != null) {
            right.left.parent = node;
        }
       
        right.left = node;
        
        Node<T> p = node.parent;
        if (null != p) {
            right.parent = p;
            if (p.left == node) {
                p.left = right;
            } else {
                p.right = right;
            }
        } else {
            root = right;
        }
        node.parent = right;
    }

    private Node<T> grandParent(Node<T> node) {
        Node<T> parent = node.parent;
        return null == parent ? null : parent.parent;
    }
    private Node<T> uncle(Node<T> node) {
        Node<T> grandParent = grandParent(node);
        if (null == grandParent) {
            return null;
        } else if (grandParent.left == node.parent) {
            return grandParent.right;
        } else {
            return grandParent.left;
        }
    }

    private Node<T> getNode(T e) {
        Node<T> node = new Node<T>();
        node.e = e;
        node.left = null;
        node.right = null;
        node.parent = null;
        node.color = Color.RED;
        
        return node;
    }
    
    public void print() {
        root.len = 32;
        markLen(root);
        
        print(root.len, root);
        System.out.println("");
        List<Node<T>> nodeList = new ArrayList<Node<T>>();
        nodeList.add(root.left);
        nodeList.add(root.right);
        print(nodeList);
    }
    
    /**
     * @param root2
     * @version 1.0.0.0 2013-1-17 下午05:07:49
     */
    private void markLen(Node<T> node) {
        if (node == null) {
            return;
        }
        if (node == root) {
            node.len = 32;
        } else {
            node.len = node.parent.left == node ? node.parent.len / 2 : node.parent.len + node.parent.len / 2;
        }
        
        if (node.left != null) {
            markLen(node.left);
        }
        if (node.right != null) {
            markLen(node.right);
        }
    }

    public void print(int layer, int len, Node<T> node) {
        if (node == root) {
            print(len, node);
            System.out.println("");
            print(layer + 1, len / 2, node.left);
            print(layer + 1, len + len / 2, node.right);
        } else if (node == null){
            return;
        } else {
            print(len, node);
        }
        
    }
    
    public void print(List<Node<T>> nodeList) {
        List<Node<T>> nodeList1 = new ArrayList<Node<T>>();
        int len = 0;
        for (Node<T> node : nodeList) {
            if (node == null) {
                continue;
            }
            print(node.len - len, node);
            len += node.len;
            if (node.left != null) {
                nodeList1.add(node.left);
            }
            if (node.right != null) {
                nodeList1.add(node.right);
            }
        }
        System.out.println();
        
        if (nodeList1.size() > 0) {
            print(nodeList1);
        }
    }
    
    /**
     * @param layer
     * @param node
     * @version 1.0.0.0 2013-1-17 下午04:44:35
     */
    private void print(int num, Node<T> node) {
        for (int i = 0; i < num; i++) {
            System.out.print(" ");
        }
        System.out.print(node.e + "(" + (node.color == Color.RED ? "红" : "黑") + ")");
    }

    /** 树中的节点 */
    private static class Node<T> {
        public T e;
        public Node left;
        public Node right;
        public Node parent;
        public Color color;
        public int len;
    }
    
    public static void main(String[] args) {
        RBTree<Integer> tree = new RBTree<Integer>();
        tree.add(12);
        tree.add(1);
        tree.add(9);
        tree.add(2);
        tree.add(0);
        tree.add(11);
        tree.add(7);
        tree.add(19);
        tree.add(4);
//        tree.add(15);
//        tree.add(18);
//        tree.add(5);
//        tree.add(14);
//        tree.add(13);
//        tree.add(10);
//        tree.add(16);
//        tree.add(6);
//        tree.add(3);
//        tree.add(8);
//        tree.add(17);
        
        tree.print();
    }
}
