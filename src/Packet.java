public class Packet {
    byte operation;
    byte answer;
    byte id;

    public Packet(byte op,byte ans, byte id){
        operation = op;
        answer = ans;
        this.id = id;
    }

    byte[] getPacket(){
         byte[] packet = new byte[3];
         for(int i=2;i>=0;i--) {
             packet[0] = (byte) (packet[0] | ((operation >> i)) << 5+i);
         }
         for(int i=2;i>=0;i--) {
             packet[0] = (byte)(packet[0] | ((answer >> i)) << 2+i);
         }
         packet[0] = (byte)(packet[0] & 0b11111100);



         return packet;
    }

}
