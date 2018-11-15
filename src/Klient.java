import java.net.*;
import java.io.*;

public class Klient implements Runnable {

    private int id;
    private Socket clientsocket;
    private DataInputStream in;
    private DataOutputStream out;
    private Serwer ser;
    private byte[] pakiet = new byte[3];
    private boolean warunek = true;

    Klient(ServerSocket ssocket, int clientid, Serwer s) {
        try {
            clientsocket = ssocket.accept();                                       // łączenie socketów ze sobą
            in = new DataInputStream(clientsocket.getInputStream());
            out = new DataOutputStream(clientsocket.getOutputStream());
            id = clientid;
            ser = s;
            wyslijpakiet(0, 0, 0);
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private byte[] pakiet(int operacja, int liczba, int id, int czas) {
        byte[] ret = new byte[3];

        ret[0] = (byte) operacja;

        ret[0] = (byte) (ret[0] | ((liczba & 0b00000111) << 3));

        ret[1] = (byte) (id & 0b00011111);

        ret[2] = (byte) (czas & 0xFF);

        return ret;
    }

    void wyslijpakiet(int operacja, int liczba, int czas) {
        try {
            out.write(pakiet(operacja, liczba, id, czas));
        } catch (IOException r) {
            System.err.println(r.getMessage());
        }
    }

    private void zakoncz() {
        try {
            clientsocket.close();
        } catch (java.io.IOException e) {
            System.err.println(e.getMessage());
        }
        finally {
            warunek = false;
        }
    }

    private void decode(byte[] data) {
        int odpowiedz, sesja, operacja;
        odpowiedz = (data[0] & 0b00111000) >> 3;
        operacja = data[0] & 0b00000111;
        sesja = data[1];
        if (sesja == id) {
            switch (operacja) {
                case 7:
                    ser.sprawdz(odpowiedz, this);
                    break;
                case 6:
                    System.out.println("Klient "+id+" kończy połączenie");
                    zakoncz();
                    break;
                default:
                    break;
            }
        }
    }

    public void run() {
        int len;
        while (warunek) {
            try {
                len = in.read(pakiet);            //  odpowiedz
                if (len == -1) {
                    System.out.println("Klient " + id + " rozłączył się");
                    warunek = false;
                    break;
                } else decode(pakiet);
            } catch (java.io.IOException e) {
                System.err.println(e.getMessage());
            }
        }


    }

}
