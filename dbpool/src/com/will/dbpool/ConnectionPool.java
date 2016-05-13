/*
 * $Header: ConnectionPool.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015-6-9 下午05:17:50
 * $Owner: will
 */
package com.will.dbpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.will.dbpool.XML.XMLNode;

/**
 * ConnectionPool 数据库连接池
 * @author will
 * @version 1.0.0.0 2015-6-9 下午05:17:50
 */
public class ConnectionPool {
    /** log */
    private static final Logger log = LoggerFactory.getLogger("com.reign.dbpool");
    
    private String name; // 数据库连接池名字
    
    private String driver;
    private String url;
    private String user;
    private String password;
    
    private int minConn;
    private int maxConn;
    
    private String testSql;
    private boolean testBeforeUse;
    private long checkInterval;
    
    private long poolCheckInterval;

    private long connAliveTime;
    
    private Queue<ProxyConnection> freeConns;
    private AtomicInteger usedConn;
    
    private Thread poolCheckThread;

    
    /**
     * 创建一个连接池
     * @param node
     * @return
     * @throws Exception
     * @version 1.0.0.0 2015-6-9 下午05:36:17
     */
    public static ConnectionPool createPool(XMLNode node) throws Exception {
        ConnectionPool pool = new ConnectionPool();
        
        pool.name = node.get("name").getValue();
        
        pool.driver = node.get("driver").getValue();
        pool.url = node.get("url").getValue();
        pool.user = node.get("user").getValue();
        pool.password = node.get("password").getValue();
        
        pool.poolCheckInterval = Long.valueOf(node.get("poolCheckInterval").getValue());

        pool.minConn = Integer.valueOf(node.get("minConn").getValue());
        pool.maxConn = Integer.valueOf(node.get("maxConn").getValue());
        pool.testSql = node.get("testSql").getValue();
        pool.testBeforeUse = Boolean.valueOf(node.get("testBeforeUse").getValue());
        pool.checkInterval = Long.valueOf(node.get("checkInterval").getValue());
        pool.connAliveTime = Long.valueOf(node.get("connAliveTime").getValue());

        pool.freeConns = new ConcurrentLinkedQueue<ProxyConnection>();
        pool.usedConn = new AtomicInteger();
        
        pool.init();
        
        log.info("init pool {} succ", pool.name);
        return pool;
    }
    
    /**
     * 获得一个连接
     * @return
     * @version 1.0.0.0 2015-6-9 下午05:45:52
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        while (freeConns.size() > 0) {
            ProxyConnection conn = freeConns.poll();
            if (null != conn && (!testBeforeUse || conn.testConnection())) {
                usedConn.incrementAndGet();
                conn.access();
                return conn;
            }
        }
        
        // 需要创建连接
        if (usedConn.get() > this.maxConn) {
            // 超过了最大值
            return null;
        } else {
            // 创建一个连接
            Connection conn = newConnection();
            usedConn.incrementAndGet();
            return conn;
        }
    }
    
    /**
     * 返回连接
     * @param conn
     * @version 1.0.0.0 2015-6-9 下午05:27:04
     */
    public void returnConnection(ProxyConnection conn) {
        try {
            resetConnection(conn);

            this.freeConns.add(conn);
            usedConn.decrementAndGet();
        } catch (Throwable t) {
            conn.realClose();
            log.error("return conn error", t);
        }
    }
    
    /**
     * 获取连接池名称
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * 释放所有连接
     * @version 1.0.0.0 2015-6-10 上午10:07:58
     */
    public void releaseAll() {
        Iterator<ProxyConnection> it = this.freeConns.iterator();
        while (it.hasNext()) {
            ProxyConnection conn = it.next();
            conn.realClose();
        }
    }

    /**
     * 初始化连接池
     * @version 1.0.0.0 2015-6-9 下午05:40:09
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    private void init() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        
        // 创建默认大小的连接池
        for (int i = 0; i < minConn; i++) {
            this.freeConns.add(newConnection());
        }
        
        // 启动检测线程
        poolCheckThread = new PoolCheckThread(name, poolCheckInterval);
        poolCheckThread.start();
    }

    /**
     * 创建一个连接
     * @return
     * @version 1.0.0.0 2015-6-9 下午05:44:39
     * @throws SQLException 
     */
    private ProxyConnection newConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        return new ProxyConnection(conn, this, testSql, checkInterval, connAliveTime);
    }

    /**
     * 检查线程池
     * @version 1.0.0.0 2015-6-9 下午05:44:04
     */
    private void poolCheck() {
        int freeSize = this.freeConns.size() - minConn;
        long checkTime = System.currentTimeMillis();
        if (freeSize > 0) {
            for (int i = 0; i < freeSize; i++) {
                ProxyConnection conn = this.freeConns.poll();
                if (null == conn) {
                    continue;
                }
                if (conn.isValid(checkTime)) {
                    this.freeConns.add(conn);
                } else {
                    conn.realClose();
                }
            }
        }
    }

    /**
     * 重置连接
     * @param conn
     * @throws SQLException
     */
    private void resetConnection(ProxyConnection conn) throws SQLException {
        if (!conn.getAutoCommit()) {
            try {
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
    
    /** 连接池检查线程 */
    private class PoolCheckThread extends Thread {
        // 检测周期
        private long interval;
        
        /**
         * 构造函数
         * @param name
         * @param poolCheckInterval
         */
        public PoolCheckThread(String name, long poolCheckInterval) {
            super(name + "-pool-checkThread");
            this.interval = poolCheckInterval;
        }

        /**
         * @see java.lang.Thread#run()
         */
        @Override
        public void run() {
            while (!this.isInterrupted()) {
                try {
                    sleep(interval);
                } catch (InterruptedException e) {
                    // Ingore
                }
                
                try {
                    poolCheck();
                } catch (Throwable t) {
                    log.error("pool check error", t);
                }
            }
        }
    }
}
