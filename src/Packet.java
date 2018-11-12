public class Packet {
    byte operation;
    byte answer;
    byte id;

    public Packet(byte op,byte ans, byte id){
        operation = op;
        answer = ans;
        this.id = id;
    }
    public Packet(){

    }

    byte[] getPacket(){
         byte[] packet = new byte[2];

         packet[0] = (byte)(operation & 0b00000111);

         packet[0] =  (byte)(packet[0] | ((answer & 0b00000111) << 3));

         packet[1] = (byte)(id & 0b00011111);

         return packet;
    }

    byte getAnswer(){
        return answer;
    }

}
