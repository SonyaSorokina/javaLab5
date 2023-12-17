import java.io.*;
import java.net.*;
import java.util.*;

public class MyClientSocket2 {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Socket server;
        boolean stop = false;
        try {
            server = new Socket( "127.0.0.1" , 8030);

            ObjectOutputStream outStr = new ObjectOutputStream(server.getOutputStream());
            ObjectInputStream inStr = new ObjectInputStream(server.getInputStream());

            Client.placeShips(inStr, outStr);

            while (true) {
                inStr.readBoolean();
                Client.makeTurn(inStr, outStr);
                if ((boolean) Await.GetObject(inStr)) {
                    System.out.println("Ты выйграл!");
                    break;
                }
            }
        } catch (Throwable e) {
            System.out.println(e);
        }
    }
}