package ch12;

import ch08.PacketCodeC;
import ch10.MessageRequestPacket;
import ch10.MessageResponsePacket;
import ch16.Session;
import ch16.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        log.info("{} 收到客户端消息: {}", LocalDateTime.now(), messageRequestPacket.getMessage());

        Session session = SessionUtil.getSession(ctx.channel());

        String userId = session.getUserId();
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(userId);
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        String toUserId = messageRequestPacket.getToUserId();
        
        log.info("sessionUserId == toUserId: {}", Objects.equals(userId, toUserId));
        Channel toUserChannel = SessionUtil.getChannel(toUserId);
        
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            log.info("{} 不在线, 发送失败", toUserId);
        }
    }

}
