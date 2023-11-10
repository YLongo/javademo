package ch09;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

public class FixedLengthFrameDecoderTest {
    
    @Test
    public void testFramesDecoded() {
        // 存储9个字节的ByteBuf
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }

        ByteBuf input = buf.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertTrue(embeddedChannel.writeInbound(input.retain()));
        Assert.assertTrue(embeddedChannel.finish());
        
        ByteBuf read = embeddedChannel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        
        read = embeddedChannel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();

        read = embeddedChannel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        
        Assert.assertNull(embeddedChannel.readInbound());
        buf.release();

    }
    
    @Test
    public void testFramesDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        
        Assert.assertTrue(channel.finish());
        
        ByteBuf read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        
        read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        
        read = channel.readInbound();
        Assert.assertEquals(buf.readSlice(3), read);
        read.release();
        
        Assert.assertNull(channel.readInbound());
        buf.release();
    }

}
