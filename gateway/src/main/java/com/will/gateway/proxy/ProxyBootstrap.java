package com.will.gateway.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.cglib.proxy.Proxy;
import org.will.framework.core.servlet.NettyConfig;
import org.will.framework.core.servlet.ServletConfig;
import org.will.framework.core.servlet.XmlConfig;
import org.will.framework.core.util.WrapperUtil;
import org.will.framework.log.InternalLoggerFactory;
import org.will.framework.log.Logger;
import org.will.framework.netty.ServletBootstrap;
import org.will.framework.netty.util.TcpConfHelper;
import org.will.framework.netty.wrapper.NettyWrapper;

/**
 * Created by WILL on 2016/8/20.
 */
public class ProxyBootstrap {
    /** log */
    protected static final Logger log = InternalLoggerFactory.getLogger(ProxyBootstrap.class);

    /** netty bootstrap */
    private Bootstrap bootstrap;

    /** TcpChildHandler */
    private TcpChildHandler tcpChildHandler;

    /**
     * 構造函數
     */
    public ProxyBootstrap() {
        this.tcpChildHandler = new TcpChildHandler();
    }

    /**
     * 構造函數
     * @param handler
     */
    public ProxyBootstrap(TcpChildHandler handler) {
        this.tcpChildHandler = handler;
    }

    /**
     * 启动
     */
    public void startup() {
        bootstrap = new Bootstrap();

        // 载入配置文件
        XmlConfig config = new XmlConfig("conf.xml");
        NettyConfig nettyConfig = config.getNettyConfig();

        // 启动Netty，读取参数
        boolean pooledBuf = (Boolean) nettyConfig.getInitParam("pooledBuf");
        boolean epoll = (Boolean) nettyConfig.getInitParam("epoll");
        int proxyThreads = (Integer) nettyConfig.getInitParam("proxyThreads");
        boolean unsafe = (Boolean) nettyConfig.getInitParam("unsafe");
        System.setProperty("io.netty.noUnsafe", String.valueOf(!unsafe));
        ByteBufAllocator allocator = pooledBuf ? new PooledByteBufAllocator(unsafe ? true : false) : new UnpooledByteBufAllocator(unsafe ? true : false);


        // 初始化Netty线程池
        EventLoopGroup proxyGroup = epoll ? new EpollEventLoopGroup(proxyThreads) : new NioEventLoopGroup(proxyThreads);
        bootstrap.group(proxyGroup)
                 .channel(epoll ? EpollSocketChannel.class : NioSocketChannel.class)
                 .handler(tcpChildHandler)
                 .option(ChannelOption.ALLOCATOR, allocator)
                 .option(ChannelOption.SO_KEEPALIVE, true);

        log.info("proxy bootstrap init succ");
    }
}
