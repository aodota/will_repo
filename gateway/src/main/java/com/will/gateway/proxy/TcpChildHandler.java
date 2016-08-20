package com.will.gateway.proxy;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.will.framework.netty.tcp.coder.MessageDecoder;
import org.will.framework.netty.tcp.coder.MessageEncoder;

/**
 * Created by WILL on 2016/8/20.
 */
public class TcpChildHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("decoder", new MessageDecoder());
        ch.pipeline().addLast("encoder", new MessageEncoder());
    }
}
