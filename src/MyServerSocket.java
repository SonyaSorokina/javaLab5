import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MyServerSocket {
    public static void main(String[] args) throws Exception {
        Socket client1, client2;
        boolean stop = false;

        ServerSocket server = new ServerSocket(8030);

        System.out.println("Сервер запущен");

        client1 = server.accept();
        client2 = server.accept();
        //TimeUnit.SECONDS.sleep(5);

        ObjectInputStream inStr = new ObjectInputStream(client1.getInputStream());
        ObjectOutputStream outStr = new ObjectOutputStream(client1.getOutputStream());

        ObjectInputStream inStr2 = new ObjectInputStream(client2.getInputStream());
        ObjectOutputStream outStr2 = new ObjectOutputStream(client2.getOutputStream());


        int[][] battlefield1 = new int[10][10];
        int[][] battlefield2 = new int[10][10];

        int[][] monitor1 = new int[10][10];
        int[][] monitor2 = new int[10][10];

        Server.placeShips(battlefield1, inStr, outStr);
        Server.placeShips(battlefield2, inStr2, outStr2);

        while (true) {
            outStr.writeBoolean(true);
            Server.makeTurn(inStr, outStr, monitor1, battlefield2);
            if (Server.isWinCondition(monitor1)) {
                Async.SendObject(true, outStr);
                break;
            }
            outStr2.writeBoolean(true);
            Server.makeTurn(inStr2, outStr2, monitor2, battlefield1);
            if (Server.isWinCondition(monitor2)) {
                Async.SendObject(true, outStr);
                break;
            }
        }
    }

}