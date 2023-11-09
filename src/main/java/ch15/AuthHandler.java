package ch15;

import ch10.LoginUtil;
import ch16.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            log.warn("not login, close");
            ctx.channel().close();
        } else {
            log.info("当前用户已登录, 准备移除AuthHandler");
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            log.info("登录验证完毕, AuthHandler将被移除");
        } else {
            log.info("无登录验证, 强制关闭连接");
        }
    }
}
