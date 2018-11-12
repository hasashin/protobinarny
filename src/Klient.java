import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Klient implements Runnable{

    int id;
    Socket clientsocket;
    DataInputStream in;
    DataOutputStream out;
    Serwer ser;
    byte[] pakiet = new byte [2];

    Klient(ServerSocket ssocket, int clientid, Serwer s){
        try{
            clientsocket = ssocket.accept();                                       // łączenie socketów ze sobą
            in = new DataInputStream(clientsocket.getInputStream());
            out = new DataOutputStream(clientsocket.getOutputStream());
            id = clientid;
            ser = s;
            wyslijpakiet(0);
        }
             catch(java.io.IOException e){}
    }

    public void wyslijpakiet(int operacja){
        try {out.write(new Packet((byte)operacja, (byte)0, (byte)id).getPacket());  }
              catch(java.io.IOException e){}
    }

    public void zakoncz(){
        try{ clientsocket.close(); }
             catch(java.io.IOException e){}
    }

    void decode(byte[] data) {
        int odpowiedź, sesja, operacja;
        odpowiedź = (data[0] & 0b00111000) >> 3;
        operacja = data[0] & 0b00000111;
        sesja = data[1];
        switch (operacja) {
            case 7:
                ser.sprawdz(odpowiedź, this);
                break;
            case 6:
                zakoncz();



            default:
                break;
        }
    }

    public void odbierzodp(){

    }

    public void run(){
        while(true){
            try {
                in.read(pakiet);            //  odpowiedz
                decode(pakiet);
            }
                 catch(java.io.IOException e){}




        }


    }

}
