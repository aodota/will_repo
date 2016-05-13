/*
 * $Header: DBPoolDriver.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015-6-10 上午10:35:29
 * $Owner: will
 */
package com.will.dbpool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DBPoolDriver
 * @author will
 * @version 1.0.0.0 2015-6-10 上午10:35:29
 */
public class DBPoolDriver implements Driver {
    /** log */
    private static final Logger log = LoggerFactory.getLogger("com.reign.dbpool");
    
    static {
        try {
            DriverManager.registerDriver(new DBPoolDriver());
            log.info("register dbpoolDriver succ");
        } catch (SQLException e) {
            log.error("register dbpoolDriver error", e);
        }
    }
    
    /**
     * @see java.sql.Driver#connect(java.lang.String, java.util.Properties)
     */
    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!url.startsWith("will")) {
            return null;
        }
        
        ConnectionPool pool = ConnectionPoolManager.getInstance().getPool(url);
        if (null == pool) {
            log.warn("can't get pool, url:{}", url);
        }
        return pool.getConnection();
    }

    /**
     * @see java.sql.Driver#acceptsURL(java.lang.String)
     */
    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url.startsWith("will");
    }

    /**
     * @see java.sql.Driver#getPropertyInfo(java.lang.String, java.util.Properties)
     */
    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    /**
     * @see java.sql.Driver#getMajorVersion()
     */
    @Override
    public int getMajorVersion() {
        return 1;
    }

    /**
     * @see java.sql.Driver#getMinorVersion()
     */
    @Override
    public int getMinorVersion() {
        return 0;
    }

    /**
     * @see java.sql.Driver#jdbcCompliant()
     */
    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    /**
     * @see java.sql.Driver#getParentLogger()
     */
    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
