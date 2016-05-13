/*
 * $Header: Environment.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2015年6月13日 下午2:38:35
 * $Owner: will
 */
package com.will.dbpool;

import java.util.HashMap;
import java.util.Map;

/**
 * Environment
 *
 * @author will
 * @version 1.0.0.0 2015年6月13日 下午2:38:35
 */
public class Environment {
    /**
     * MAP
     */
    public static final Map<String, String> ENVIRONMENT = new HashMap<String, String>(4);

    static {
        ENVIRONMENT.put("componentName", "dbpool");
        ENVIRONMENT.put("version", "1.0.0.0");
    }
}
