package ch10;

import ch08.Command;
import ch08.Packet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageRequestPacket extends Packet {

    private String toUserId;
    
    private String message;
    
    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
