/*
 * $Header: MessageDecode.java
 * $Revision: 1.0.0.0
 * $CreateDate: 2011-7-9 下午03:20:37
 * $Owner: wangys
 */
package com.will.gateway.server.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.will.framework.netty.tcp.handler.RequestMessage;

import java.util.List;


/**
 * MessageDecoder
 * @author wangys
 * @version 1.0.0.0 2011-7-9 下午03:20:37
 */
public class MessageDecoder extends ByteToMessageDecoder {
    /**
     * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, List)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return; // 不完整的包
        }
        
        int dataLen = in.getInt(in.readerIndex());
        if (in.readableBytes() < dataLen + 4) {
            return; // 不完整的包
        }
        
        in.skipBytes(4);
        
        RequestMessage r = new RequestMessage();
        byte[] commandArray = new byte[32];
        in.readBytes(commandArray);
        r.setCommand(new String(commandArray).trim());
        r.setRequestId(in.readInt());
        byte[] contentBytes = new byte[dataLen - 36];
        in.readBytes(contentBytes);
        r.setContent(contentBytes);
        System.out.println(new String(r.getContent()));
        out.add(r);
    }

	
}
