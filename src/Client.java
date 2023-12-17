import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    public static void placeShips(ObjectInputStream inStr, ObjectOutputStream outStr){
        int deck = 4;

        while (deck >= 1) {
            System.out.println(Await.GetObject(inStr));
            System.out.println(Await.GetObject(inStr));
            System.out.println(Await.GetObject(inStr));

            var var = (ArrayList<ArrayList<Integer>>) Await.GetObject(inStr);
            int[][] arr = new int[10][10];
            int k = 0;

            for(var i : var){
                for(var j : i){
                    arr[k / 10][k % 10] = j;
                    k++;
                }
            }
            drawField(arr);

            System.out.println(Await.GetObject(inStr));
            Async.SendObject(scanner.nextInt(), outStr);
            System.out.println(Await.GetObject(inStr));
            Async.SendObject(scanner.nextInt(), outStr);

            System.out.println(Await.GetObject(inStr));
            System.out.println(Await.GetObject(inStr));
            System.out.println(Await.GetObject(inStr));
            Async.SendObject(scanner.nextInt(), outStr);

            if (!(boolean)(Await.GetObject(inStr))) {
                System.out.println(Await.GetObject(inStr));
                continue;
            }

            deck--;
            clearScreen();
        }
    }

    public static void makeTurn(ObjectInputStream inStr, ObjectOutputStream outStr) {
        int[][] monitor;
        while (true) {

            var var = (ArrayList<ArrayList<Integer>>) Await.GetObject(inStr);

            monitor = new int[10][10];
            int k = 0;

            for(var i : var){
                for(var j : i){
                    monitor[k / 10][k % 10] = j;
                    k++;
                }
            }

            System.out.println("Сделайте свой ход");
            System.out.println("  0 1 2 3 4 5 6 7 8 9");
            for (int i = 0; i < monitor.length; i++) {
                System.out.print(i + " ");
                for (int j = 0; j < monitor[1].length; j++) {
                    if (monitor[j][i] == 0) {
                        System.out.print("- ");
                    } else if (monitor[j][i] == 1) {
                        System.out.print(". ");
                    } else {
                        System.out.print("X ");
                    }
                }
                System.out.println();
            }

            System.out.println(Await.GetObject(inStr));
            Async.SendObject(scanner.nextInt(), outStr);
            System.out.println(Await.GetObject(inStr));
            Async.SendObject(scanner.nextInt(), outStr);

            if ((boolean) Await.GetObject(inStr)) {
                System.out.println(Await.GetObject(inStr));
            } else {
                System.out.println(Await.GetObject(inStr));
                break;
            }
            clearScreen();
        }
    }

    public static void drawField(int[][] battlefield) {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < battlefield.length; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < battlefield[1].length; j++) {
                if (battlefield[j][i] == 0) {
                    System.out.print("- ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
    public static void clearScreen(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
