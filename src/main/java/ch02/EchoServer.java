package ch02;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Data
@Slf4j
@RequiredArgsConstructor
public class EchoServer {
    
    private final int port;

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            log.error("usage: {} port", EchoServer.class.getSimpleName());
            return;
        }
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }
    
    public void start() throws InterruptedException {
        EchoServerHandler echoServerHandler = new EchoServerHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 表示使用NIO进行连接的创建
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        // 每当创建一个新连接时都会执行该方法将handler添加到pipeline中  
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(echoServerHandler);
                        }
                    });
            
            // 异步绑定到服务器，调用sync方法阻塞，直到绑定完成
            ChannelFuture channelFuture = b.bind().sync();
            // 
            channelFuture.channel().closeFuture().sync();
            
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}
