package com.will.toolkit.zookeeper;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * ZkUtils zookeeper 工具类
 * Created by WILL on 2016/7/16.
 */
public final class ZkUtils {
    private ZkUtils() {};

    /**
     * 安全删除一个节点
     * @param zk
     * @param node
     * @param version
     * @return
     */
    public static boolean safeDelete(ZooKeeper zk, String node, int version) throws Exception {
        try {
            zk.delete(node, version);
            return true;
        } catch (KeeperException e) {
            if(e.code() != KeeperException.Code.NONODE) {
                throw e;
            }
            return false;
        }
    }

    /**
     * 安全删除一个节点, 并且递归删除下面所有节点
     * @param zk
     * @param node
     * @param version
     * @return
     */
    public static boolean recursiveSafeDelete(ZooKeeper zk, String node, int version) throws Exception {
        try {
            List<String> childrens = zk.getChildren(node, false);
            for (String child : childrens) {
                recursiveSafeDelete(zk, node + "/" + child, version);
            }
            safeDelete(zk, node, version);
            return true;
        } catch (KeeperException e) {
            if(e.code() != KeeperException.Code.NONODE) {
                throw e;
            }
            return false;
        }
    }

    /**
     * 安全创建一个节点，如果节点本身就存在，节点数据不会被更新
     * @param zk
     * @param node
     * @param data
     * @param privileges
     * @param createMode
     * @return
     * @throws Exception
     */
    public static String safeCreate(ZooKeeper zk, String node, byte[] data, List<ACL> privileges, CreateMode createMode) throws Exception {
        try {
            if(StringUtils.isBlank(node)) {
                return node;
            }
            return zk.create(node, data, privileges, createMode);
        } catch (KeeperException e) {
            if(e.code() != KeeperException.Code.NODEEXISTS) {
                throw e;
            }
            return node;
        }
    }

    /**
     * 安全创建一个节点，如果节点本身就存在，节点数据不会被更新
     * @param zk
     * @param node
     * @param data
     * @param privileges
     * @param createMode
     * @return
     * @throws Exception
     */
    public static String recursiveSafeCreate(ZooKeeper zk, String node, byte[] data, List<ACL> privileges, CreateMode createMode) throws Exception {
        if (StringUtils.isBlank(node)) {
            return node;
        } else if ("/".equals(node)) {
            return node;
        }

        // 递归创建
        int index = node.lastIndexOf("/");
        if (index == -1) {
            return node;
        }
        String parent = node.substring(0, index);
        recursiveSafeCreate(zk, parent, data, privileges, createMode);


        return safeCreate(zk, node, data, privileges, createMode);
    }

    /**
     * 安全获取一个节点数据
     * @param zk
     * @param node
     * @param watcher
     * @param stat
     * @return
     * @throws Exception
     */
    public static byte[] saftGetData(ZooKeeper zk, String node, Watcher watcher, Stat stat) throws Exception {
        try {
            return zk.getData(node, watcher, stat);
        } catch (KeeperException e) {
            if (e.code() != KeeperException.Code.NONODE) {
                throw e;
            }
            return null;
        }
    }

    /**
     * 安全获取一个节点数据
     * @param zk
     * @param node
     * @param watch
     * @param stat
     * @return
     * @throws Exception
     */
    public static byte[] saftGetData(ZooKeeper zk, String node, boolean watch, Stat stat) throws Exception {
        try {
            return zk.getData(node, watch, stat);
        } catch (KeeperException e) {
            if (e.code() != KeeperException.Code.NONODE) {
                throw e;
            }
            return null;
        }
    }

    /**
     * 安全获取一个节点数据
     * @param zk
     * @param node
     * @param watch
     * @return
     * @throws Exception
     */
    public static byte[] saftGetData(ZooKeeper zk, String node, boolean watch) throws Exception {
        try {
            return zk.getData(node, watch, null);
        } catch (KeeperException e) {
            if (e.code() != KeeperException.Code.NONODE) {
                throw e;
            }
            return null;
        }
    }
}
