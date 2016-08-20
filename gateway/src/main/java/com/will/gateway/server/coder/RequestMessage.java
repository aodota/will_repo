/*
 * $Header: RequestMessage.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-12 上午11:07:18
 * $Owner: wangys
 */
package com.will.gateway.server.coder;

/**
 * @author wangys
 * @version 1.0.0.0 2011-7-12 上午11:07:18
 *
 */
public class RequestMessage {
	/** requestId */
	private int requestId;
	
	/** content */
	private byte[] content;
	
	/** command */
	private String command;
	
	/** sessionId */
	private String sessionId;
	
	/**
	 * 构造函数
	 */
	public RequestMessage() {
	    
	}
	
	/**
	 * RequestMessage
	 * 
	 * @param requestId
	 * @param content
	 * @param command
	 */
	public RequestMessage(int requestId, String command, byte[] content) {
		super();
		this.requestId = requestId;
		this.content = content;
		this.command = command;
	}
	
	public void setRequestMessage(org.will.framework.netty.tcp.handler.RequestMessage message) {
	    this.requestId = message.getRequestId();
	    this.command = message.getCommand();
	    this.content = message.getContent();
	    this.sessionId = message.getSessionId();
	}

	/**
	 * @return the requestId
	 */
	public int getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
	
	
}
