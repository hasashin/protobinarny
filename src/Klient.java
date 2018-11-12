import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Klient {

    int id;
    int odp;
    Socket socket;

    Klient(InetAddress ip, int port){
        socket = new Socket(ip,port);
    }

    public void uzyskajid{
        //odbierz od serwera
    }

    public void przeslijodp(){
        Scanner odczyt = new Scanner(System.in);
        odp = Integer.parseInt(odczyt.nextLine());
        //przeslij do serwera
    }

    public void polacz(){}

    public void zakoncz(){}

}
