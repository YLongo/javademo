package ch09;

import ch08.LoginRequestPacket;
import ch08.Packet;
import ch08.PacketCodeC;
import ch10.MessageRequestPacket;
import ch10.MessageResponsePacket;
import com.sun.xml.internal.ws.api.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);
        
        if (packet instanceof LoginRequestPacket) {
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
            if (this.valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                log.info("{} 登录成功", LocalDateTime.now());
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                log.info("{} 登录失败", LocalDateTime.now());
            }
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if (packet instanceof MessageRequestPacket) {
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;
            log.info("{} 收到客户端消息: {}", LocalDateTime.now(), messageRequestPacket.getMessage());

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("服务端回复:[" + messageRequestPacket.getMessage() + "]");

            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
        
        
    }
    
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
