/*
 * $Header: TcpHandler.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2012-7-25 下午03:27:51
 * $Owner: wangys
 */
package com.will.gateway.server.tcp.handler;

import io.netty.channel.ChannelInboundHandler;
import org.will.framework.core.servlet.Servlet;
import org.will.framework.core.servlet.ServletContext;


/**
 * TcpHandler
 * @author wangys
 * @version 1.0.0.0 2012-7-25 下午03:27:51
 */
public interface TcpHandler extends ChannelInboundHandler {
    
    /**
     * 设置ServletContext
     * @param context
     * @version 1.0.0.0 2012-7-25 下午03:28:28
     */
    void setServletContext(ServletContext context);
    
    /**
     * 设置Servlet
     * @param servlet
     * @version 1.0.0.0 2012-7-25 下午03:28:51
     */
    void setServlet(Servlet servlet);
}
