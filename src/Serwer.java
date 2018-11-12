import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.Period;
import java.util.Date;
import java.util.Random;
import java.util.Vector;


public class Serwer {

    int id1;
    int id2;
    int czasrozgrywki;
    //LocalTime poczatkowyczas;
    long poczatkowy;
    int liczba;
    Packet pakiet = new Packet();
    ServerSocket socket;


    Serwer(int port){
        try{socket = new ServerSocket(port); }
        catch(java.io.IOException e){}
    }

    public void generuj() {
        Random generator = new Random();
        id1 = (generator.nextInt(30) + 1);
        id2 = (generator.nextInt(30) + 1);
    }

    public void maxczas() {
        czasrozgrywki = (Math.abs(id1 - id2) * 74) % 90 + 24;
    }

    public void losujliczbe() {
        Random generator = new Random();
        liczba = (generator.nextInt(7));
    }

    public void sprawdz() {
        if (pakiet.getAnswer() == liczba){
           // System.out.println("Wartosc odgadnieta! Tajna liczba to: " + liczba);
        }
        if (pakiet.getAnswer() < liczba){
           // System.out.println("Za mało! Probuj dalej");
        }
        if (pakiet.getAnswer() > liczba){
           // System.out.println("Za duzo! Probuj dalej");
        }
    }

    public void ileczasu(){
        long obecny = System.currentTimeMillis()/1000;
        long uplynelo = obecny - poczatkowy;
        long zostalo = czasrozgrywki - uplynelo;
        System.out.println("Zostalo " + zostalo + " sekund");

        // LocalTime obecny = LocalTime.now();
        //long epoch = System.currentTimeMillis()/1000;
        // aktualny - poczatkowy = ile upłynęło od uruchomienia
        // czas max - ile uplynelo od uruchomienia = zostało
    }

    public void wyslijpakiet(){

    }

    public void start(){
        generuj();
        Klient k1 = new Klient(socket, id1);
        Klient k2 = new Klient(socket, id2);
        //poczatkowyczas = LocalTime.now();
        long poczatkowy = System.currentTimeMillis()/1000;
        //ileczasu();
        maxczas();
        losujliczbe();


    }

}