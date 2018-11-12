import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Klient implements Runnable{

    int id;
    int odp;
    Socket clientsocket;
    DataInputStream in;
    DataOutputStream out;

    Klient(ServerSocket ssocket, int clientid){
        try{
            clientsocket = ssocket.accept();   // łączenie socketów ze sobą
            in = new DataInputStream(clientsocket.getInputStream());
            out = new DataOutputStream(clientsocket.getOutputStream());
            id = clientid;
        }
             catch(java.io.IOException e){}
    }

    public void przeslijodp(){
        Scanner odczyt = new Scanner(System.in);
        odp = Integer.parseInt(odczyt.nextLine());
        try {out.write(new Packet((byte)0, (byte)odp, (byte)id).getPacket());  }
              catch(java.io.IOException e){}
    }

    public void zakoncz(){
        try{ clientsocket.close(); }
             catch(java.io.IOException e){}
    }

    public void run(){


    }

}
