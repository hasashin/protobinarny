import java.util.Random;
import java.util.Vector;


public class Serwer {

    int id1;
    int id2;
    int czasrozgrywki;
    int liczba;
    Packet pakiet = new Packet();



    public void generuj() {
        Random generator = new Random();
        id1 = (generator.nextInt(30) + 1);
        id2 = (generator.nextInt(30) + 1);
    }

    public void maxczas() {
        czasrozgrywki = (Math.abs(id1 - id2) * 74) % 90 + 24;
    }

    public void losuj() {
        Random generator = new Random();
        liczba = (generator.nextInt(1024));
    }

    public void sprawdz() {
        if (pakiet.getanswer() == liczba){
            System.out.println("Wartosc odgadnieta! Tajna liczba to: " + liczba);
        }
        if (pakiet.getanswer() < liczba){
            System.out.println("Za mało! Probuj dalej");
        }
        if (pakiet.getanswer() > liczba){
            System.out.println("Za duzo! Probuj dalej");
        }
    }

    public void ileczasu(){
        //System.out.println("Zostalo " + );
    }

    public void start(){

    }

}