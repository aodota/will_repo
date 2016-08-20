/*
 * $Header: DefaultHandler.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-9 下午03:21:10
 * $Owner: wangys
 */
package com.will.gateway.server.tcp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.will.framework.core.servlet.Request;
import org.will.framework.core.servlet.Response;
import org.will.framework.core.servlet.Servlet;
import org.will.framework.core.servlet.ServletContext;
import org.will.framework.netty.NettyConstants;
import org.will.framework.netty.tcp.TcpRequest;
import org.will.framework.netty.tcp.TcpResponse;
import org.will.framework.netty.tcp.handler.RequestMessage;
import org.will.framework.netty.tcp.handler.TcpHandler;


/**
 * TcpDefaultHandler
 * @author wangys
 * @version 1.0.0.0 2011-7-9 下午03:21:10
 */
public class TcpDefaultHandler extends ChannelInboundHandlerAdapter implements TcpHandler {
	/** 命令处理器 */
	private Servlet servlet;
	
	/** 系统应用环境 */
	private ServletContext sc;
	
	/**
	 * 默认构造函数
	 */
	public TcpDefaultHandler() {
	    
	}
	
	/**
	 * 构造函数
	 * @param servlet
	 */
	public TcpDefaultHandler(Servlet servlet, ServletContext sc) {
		this.servlet = servlet;
		this.sc = sc;
	}

	
	/**
     * @see org.will.framework.netty.tcp.handler.TcpHandler#setServletContext(org.will.framework.netty.servlet.ServletContext)
     */
    @Override
    public void setServletContext(ServletContext context) {
        this.sc = context;
    }

    /**
     * @see org.will.framework.netty.tcp.handler.TcpHandler#setServlet(org.will.framework.netty.servlet.Servlet)
     */
    @Override
    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    /**
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, Object)
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RequestMessage) {
            RequestMessage message = (RequestMessage) msg;
            message.setSessionId((String) ctx.attr(NettyConstants.SESSION_ID).get());

            Response response = new TcpResponse(ctx.channel());
            Request request = new TcpRequest(ctx, sc, ctx.channel(), message);
            servlet.service(request, response);
        }
    }

    /**
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    /**
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, Throwable)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
    }
}
