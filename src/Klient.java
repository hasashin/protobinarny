import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Klient implements Runnable {

    int id;
    Socket clientsocket;
    DataInputStream in;
    DataOutputStream out;
    Serwer ser;
    byte[] pakiet = new byte[3];

    Klient(ServerSocket ssocket, int clientid, Serwer s) {
        try {
            clientsocket = ssocket.accept();                                       // łączenie socketów ze sobą
            in = new DataInputStream(clientsocket.getInputStream());
            out = new DataOutputStream(clientsocket.getOutputStream());
            id = clientid;
            ser = s;
            wyslijpakiet(0, 0);
        } catch (java.io.IOException e) {
        }
    }

    byte[] pakiet(int operacja, int liczba, int id, int czas) {
        byte[] ret = new byte[3];

        ret[0] = (byte) operacja;

        ret[0] = (byte) (ret[0] | ((liczba & 0b00000111) << 3));

        ret[1] = (byte) (id & 0b00011111);

        ret[2] = (byte) (czas & 0b11111111);

        return ret;
    }

    public void wyslijpakiet(int operacja, int liczba, int czas) {
        try {
            out.write(pakiet(operacja, liczba, id, czas));
        } catch (IOException r) {
        }
    }

    public void zakoncz() {
        try {
            clientsocket.close();
        } catch (java.io.IOException e) {
        }
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

    public void odbierzodp() {

    }

    public void run() {
        while (ser.warunek) {
            try {
                in.read(pakiet);            //  odpowiedz
                decode(pakiet);
            } catch (java.io.IOException e) {
            }
        }


    }

}
