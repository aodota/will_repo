/*
 * $Header: MessageEncode.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-9 下午03:20:45
 * $Owner: wangys
 */
package com.will.gateway.server.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MessageEncoder
 * @author wangys
 * @version 1.0.0.0 2011-7-9 下午03:20:45
 *
 */
public class MessageEncoder extends MessageToByteEncoder<ByteBuf> {
    /**
     * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, Object, io.netty.buffer.ByteBuf)
     */
    @Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		out.writeBytes(msg, msg.readerIndex(), msg.readableBytes());
	}


}
