/*
 * $Header: TcpServletChildHandler.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2013-11-19 下午01:11:33
 * $Owner: wangys
 */
package com.will.gateway.server.tcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.will.framework.core.servlet.NettyConfig;
import org.will.framework.core.servlet.Servlet;
import org.will.framework.core.servlet.ServletContext;
import org.will.framework.netty.tcp.coder.MessageDecoder;
import org.will.framework.netty.tcp.coder.MessageEncoder;
import org.will.framework.netty.tcp.handler.TcpHandler;


/**
 * TcpServletChildHandler 
 * @author wangys
 * @version 1.0.0.0 2013-11-19 下午01:11:33
 */
public class TcpServletChildHandler extends ChannelInitializer<SocketChannel> {
    /** servlet */
    private final Servlet servlet;
    
    /** ServletContext */
    private final ServletContext sc;
    
    /** tcpHandlerClass */
    private final Class<?> tcpHandlerClass;
    
    /**
     * TcpServletChildHandler 构造函数
     * @param servlet
     * @param servletContext
     * @param nettyConfig
     * @throws Exception 
     */
    public TcpServletChildHandler(Servlet servlet, ServletContext servletContext, NettyConfig config) throws Exception {
        this.servlet = servlet;
        this.sc = servletContext;
        
        // 查找ChannelHandler
        String tcpHandlerClassName = (String) config.getInitParam("tcpHandler");
        Class<?> clazz = getClass().getClassLoader().loadClass(tcpHandlerClassName);
        if (null == clazz) {
            throw new ClassNotFoundException(tcpHandlerClassName);
        } else if (!TcpHandler.class.isAssignableFrom(clazz)) {
            throw new ClassCastException(clazz.getName() + " can't cast to " + TcpHandler.class.getName());
        }
        this.tcpHandlerClass = clazz;
        
    }

    /**
     * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 初始化ChannelHandler
        TcpHandler handler = (TcpHandler) tcpHandlerClass.newInstance();
        handler.setServlet(servlet);
        handler.setServletContext(sc);
        
//        ch.pipeline().addLast("flashPolicy", new FlashPolicyHandler());
        ch.pipeline().addLast("decoder", new MessageDecoder());
        ch.pipeline().addLast("encoder", new MessageEncoder());
        ch.pipeline().addLast("handler", handler);
    }

    

}
