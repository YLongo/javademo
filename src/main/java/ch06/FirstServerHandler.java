package ch06;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @since 2023-11-06 14:19
 */
@Slf4j
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("{} : 服务端读到数据 -> {}", LocalDateTime.now(), byteBuf.toString(StandardCharsets.UTF_8));
        
        log.info("服务端写入数据");
        ctx.channel().writeAndFlush(getByteBuf(ctx));
        
    }
    
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "world fuck".getBytes(StandardCharsets.UTF_8);
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }    
}
