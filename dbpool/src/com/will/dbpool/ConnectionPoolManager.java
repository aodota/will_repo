/*
 * $Header: ConnectionPoolManager.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015-6-10 上午10:09:11
 * $Owner: will
 */
package com.will.dbpool;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.will.dbpool.XML.XMLNode;

/**
 * ConnectionPoolManager
 * @author will
 * @version 1.0.0.0 2015-6-10 上午10:09:11
 */
public class ConnectionPoolManager {
    /** instance */
    private static final ConnectionPoolManager instance = new ConnectionPoolManager();
    
    /** poolMap */
    private Map<String, ConnectionPool> poolMap = new HashMap<String, ConnectionPool>();
    
    /**
     * 获取实例
     * @return
     * @version 1.0.0.0 2015-6-10 上午10:10:41
     */
    public static ConnectionPoolManager getInstance() {
        return instance;
    }
    
    /**
     * 初始化配置文件
     * @param inputStream
     * @version 1.0.0.0 2015-6-10 上午10:33:25
     * @throws Exception 
     */
    public void configure(InputStream inputStream) throws Exception {
        XML xml = new XML(inputStream);
        
        List<XMLNode> nodeList = xml.getList("db");
        for (XMLNode xmlNode : nodeList) {
            ConnectionPool pool = ConnectionPool.createPool(xmlNode);
            poolMap.put(pool.getName(), pool);
        }
    }
    
    /**
     * 获取连接池
     * @param name
     * @return
     * @version 1.0.0.0 2015-6-10 上午10:35:00
     */
    public ConnectionPool getPool(String name) {
        return poolMap.get(name);
    }
}
