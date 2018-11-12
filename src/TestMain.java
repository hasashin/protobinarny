public class TestMain {

    public static void main(String[] args){
        Packet pakiet = new Packet((byte)5,(byte)4,(byte)30);
        for(byte bajt :pakiet.getPacket())
        for(int i=0;i<8;i++){
            System.out.print(bajt << i);
        }
    }

}
