package ch04;

import ch12.LoginRequestHandler;
import ch12.MessageRequestHandler;
import ch12.PacketDecoder;
import ch12.PacketEncoder;
import ch13.FirstServerHandler;
import ch13.Splitter;
import ch14.LifeCyCleTestHandler;
import ch15.AuthHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @since 2023-11-06 11:49
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                // handler()方法用于指定在服务端启动过程中的一些逻辑
//                .handler(new ChannelInitializer<NioServerSocketChannel>() {
//                    @Override
//                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
//                        log.info("channel starting ...");
//                    }
//                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                // childHandler()方法用于指定处理新连接数据的读写处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

//                                .addLast(new FirstServerHandler())
//                        ch.pipeline().addLast(new ServerHandler());
//                        ch.pipeline().addLast(new InBoundHandlerA());
//                        ch.pipeline().addLast(new InBoundHandlerB());
//                        ch.pipeline().addLast(new InBoundHandlerC());

//                        ch.pipeline().addLast(new OutboundHandlerA());
//                        ch.pipeline().addLast(new OutboundHandlerB());
//                        ch.pipeline().addLast(new OutboundHandlerC());
                        ch.pipeline()
//                                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 7, 4))
                                .addLast(new Splitter())
//                                .addLast(new FirstServerHandler())
//                                .addLast(new LifeCyCleTestHandler())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, 1000);

    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("端口{}绑定成功", port);
            } else {
                log.info("端口{}绑定失败", port);
                bind(serverBootstrap, port + 1);
            }
        });
    }
}
