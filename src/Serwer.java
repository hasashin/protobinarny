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
    boolean warunek=true;
    ServerSocket socket;
    Klient k1;
    Klient k2;


    Serwer(int port){
        try{socket = new ServerSocket(port); }
        catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
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
        System.out.println("Wybrano " + liczba);
    }

    public void sprawdz(int odp, Klient k) {
        if (odp == liczba){
            k.wyslijpakiet(3,0,0);
            k.wyslijpakiet(6,0,0);
            if(k.equals(k1)){
                k2.wyslijpakiet(5,liczba,0);
            }
            else{
                k1.wyslijpakiet(5,liczba,0);
            }
            warunek = false;
        }
        else{
            k.wyslijpakiet(2,0,0);
        }
    }

    public void ileczasu(){
        long obecny = System.currentTimeMillis()/1000;
        long uplynelo = obecny - poczatkowy;
        long zostalo = czasrozgrywki - uplynelo;
        if(zostalo > 0){
            System.out.println("Zostalo " + zostalo + " sekund");
            k1.wyslijpakiet(1,0,(int)zostalo);
            k2.wyslijpakiet(1,0,(int)zostalo);
        }
        else{
            k1.wyslijpakiet(6,0,0);
            k2.wyslijpakiet(6,0,0);
            warunek = false;
        }


        // LocalTime obecny = LocalTime.now();
        //long epoch = System.currentTimeMillis()/1000;
        // aktualny - poczatkowy = ile upłynęło od uruchomienia
        // czas max - ile uplynelo od uruchomienia = zostało
    }

    public void start(){
        generuj();
        k1 = new Klient(socket, id1, this);
        k2 = new Klient(socket, id2, this);

        //poczatkowyczas = LocalTime.now();
        maxczas();
        losujliczbe();
        poczatkowy = System.currentTimeMillis()/1000;
        //ileczasu();

        Thread f1 = new Thread(k1);
        Thread f2 = new Thread(k2);

        k1.wyslijpakiet(4, 0,0);
        k2.wyslijpakiet(4, 0,0);

        f1.start();
        f2.start();

        long pietnascie = System.currentTimeMillis()/1000;

        while(warunek) {
            if((System.currentTimeMillis()/1000 - pietnascie) > 14){
                ileczasu();
                pietnascie = System.currentTimeMillis()/1000;
            }
        }

        f1.interrupt();
        f2.interrupt();
    }

}