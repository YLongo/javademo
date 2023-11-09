package ch12;

import ch08.LoginRequestPacket;
import ch09.LoginResponsePacket;
import ch10.LoginUtil;
import ch16.Session;
import ch16.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        if (loginResponsePacket.isSuccess()) {
            String userId = loginResponsePacket.getUserId();
            String userName = loginResponsePacket.getUserName();
            SessionUtil.bindSession(new Session(userId, userName), ctx.channel());
            log.info("{} 客户端登录成功, userId:{}", LocalDateTime.now(), userId);
        } else {
            log.info("{} 客户端登录失败, 原因: ", loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} 客户端开始登录", LocalDateTime.now());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接被关闭");
    }
}
