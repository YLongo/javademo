package ch12;

import ch08.LoginRequestPacket;
import ch08.PacketCodeC;
import ch09.LoginResponsePacket;
import ch10.LoginUtil;
import ch16.Session;
import ch16.SessionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginResponsePacket.getVersion());
        
        String username = loginRequestPacket.getUsername();
        loginResponsePacket.setUserName(username);
        
        log.info("当前登录人: {}", username);
        
        if (this.valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            
            // 标记登录成功
            SessionUtil.bindSession(new Session(userId, username), ctx.channel());
            log.info("{} 登录成功", LocalDateTime.now());
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            log.info("{} 登录失败", LocalDateTime.now());
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }


    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket != null;
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
