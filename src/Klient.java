import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Klient {

    int id;
    int odp;
    Socket clientsocket;

    Klient(ServerSocket ssocket, int clientid){
        try{
            clientsocket = ssocket.accept();   // łączenie socketów ze sobą
            id = clientid;
        }
        catch(java.io.IOException e){}
    }

    public void uzyskajid() {
        //odbierz od serwera
    }

    public void przeslijodp(){
        Scanner odczyt = new Scanner(System.in);
        odp = Integer.parseInt(odczyt.nextLine());
        //przeslij do serwera
    }

    public void polacz(){
        // uzyskaj server socket
    }

    public void zakoncz(){
        try{ clientsocket.close(); }
        catch(java.io.IOException e){}
    }

}
