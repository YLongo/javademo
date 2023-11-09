package ch07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf(9, 100)", buffer);
        
        // 写入之后占用4个字节
        buffer.writeBytes(new byte[] {1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);
        
        // 写入之后占4个字节
        buffer.writeInt(12);
        print("writeInt(12)", buffer);
        
        // 写入之后占1个字节, 此时总占用9个字节, buffer不可写
        buffer.writeBytes(new byte[] {5});
        print("writeBytes(5)", buffer);
        
        // 需要扩容, 写入占用一个字节
        buffer.writeBytes(new byte[] {6});
        print("writeBytes(6)", buffer);
        
        log.info("getByte(3) = {}", buffer.getByte(3));
        log.info("getShort(3) = {}", buffer.getShort(3));
        log.info("getInt(3) = {}", buffer.getInt(3));
        print("getByte()不改变读写指针", buffer);
        
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()不改变读写指针", buffer);
        
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes()改变读写指针", buffer);
        
    }
    
    
    private static void print(String action, ByteBuf buffer) {
        log.info("after === {} ===", action);
        log.info("capacity(): {}", buffer.capacity());
        log.info("maxCapacity(): {}", buffer.maxCapacity());
        log.info("readerIndex(): {}", buffer.readerIndex());
        log.info("readableBytes(): {}", buffer.readableBytes());
        log.info("isReadable(): {}", buffer.isReadable());
        log.info("writerIndex(): {}", buffer.writerIndex());
        log.info("writableBytes(): {}", buffer.writableBytes());
        log.info("isWritable(): {}", buffer.isWritable());
        log.info("maxWritableBytes(): {}", buffer.maxWritableBytes());
        log.info("\n");
    }
}
