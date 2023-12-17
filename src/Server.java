import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    static String playerName1 = "Player#1";
    static String playerName2 = "Player#2";
    static int[][] battlefield1 = new int[10][10];
    static int[][] battlefield2 = new int[10][10];
    static int[][] monitor1 = new int[10][10];
    static int[][] monitor2 = new int[10][10];

    public static void placeShips(int[][] battlefield, ObjectInputStream inStr, ObjectOutputStream outStr) throws IOException {
        int deck = 4;

        while (deck >= 1) {
            Async.SendObject("", outStr);
            Async.SendObject("Расположите свой " + deck + "-палубный корабль", outStr);
            Async.SendObject("", outStr);

            ArrayList<ArrayList<Integer>> ar = new ArrayList<ArrayList<Integer>>();

            ArrayList<Integer> intList;
            for (int[] i : battlefield)
            {
                intList = new ArrayList<Integer>(i.length);
                for (int j : i){
                    intList.add(j);
                }
                ar.add(intList);
            }

            Async.SendObject(ar, outStr);

            Async.SendObject("Введите координаты по x", outStr);
            int x = (int) Await.GetObject(inStr);
            Async.SendObject("Введите координаты по y", outStr);
            int y = (int) Await.GetObject(inStr);

            Async.SendObject("Горизонтально или вертикально?", outStr);
            Async.SendObject("1. Вертикально.", outStr);
            Async.SendObject("2. Горизонтально.", outStr);

            int direction = (int) Await.GetObject(inStr);

            if (!isAvailable(x, y, deck, direction, battlefield)){
                Async.SendObject(false, outStr);
                Async.SendObject("Некоректное размещение. Попробуйте снова", outStr);
                continue;
            } else{
                Async.SendObject(true, outStr);
            }

            for (int i = 0; i < deck; i++) {
                if (direction == 1) {
                    battlefield[x][y + i] = 1;
                } else {
                    battlefield[x + i][y] = 1;
                }
            }

            deck--;
        }
    }

    public static void makeTurn(ObjectInputStream inStr, ObjectOutputStream outStr, int[][] monitor, int[][] battlefield) {
        while (true) {
            System.out.println("Сделайте свой ход");
            System.out.println("  0 1 2 3 4 5 6 7 8 9");

            ArrayList<ArrayList<Integer>> monik = new ArrayList<ArrayList<Integer>>();

            ArrayList<Integer> intList;
            for (int[] i : battlefield)
            {
                intList = new ArrayList<Integer>(i.length);
                for (int j : i){
                    intList.add(j);
                }
                monik.add(intList);
            }

            Async.SendObject(monik, outStr);

            Async.SendObject("Введите координаты по x", outStr);
            int x = (int) Await.GetObject(inStr);
            Async.SendObject("Введите координаты по y", outStr);
            int y = (int) Await.GetObject(inStr);

            if (battlefield[x][y] == 1) {
                Async.SendObject(true, outStr);
                Async.SendObject("Попал! Сделайте ход снова", outStr);
                monitor[x][y] = 2;
            } else {
                Async.SendObject(false, outStr);
                Async.SendObject("Промах!", outStr);
                monitor[x][y] = 1;
                break;
            }
        }
    }
    public static boolean isWinCondition(int[][] monitor) {
        int counter = 0;
        for (int i = 0; i < monitor.length; i++) {
            for (int j = 0; j < monitor[i].length; j++) {
                if (monitor[i][j] == 2) {
                    counter++;
                }
            }
        }
        if (counter >= 10) {
            System.out.println("Победа!");
            return true;
        }
        return false;
    }
    public static boolean isAvailable(int x, int y, int deck, int rotation, int[][] battlefield) {
        // out of bound check
        if (rotation == 1) {
            if (y + deck > battlefield.length) {
                return false;
            }
        }
        if (rotation == 2){
            if (x + deck > battlefield[0].length){
                return false;
            }
        }
        while (deck!=0){
            for (int i = 0; i < deck; i++) {
                int xi = 0;
                int yi = 0;
                if (rotation == 1){
                    yi = i;
                } else{
                    xi = i;
                }
                if (x + 1 + xi < battlefield.length && x + 1 + xi >= 0){
                    if (battlefield[x + 1 + xi][y + yi]!=0){
                        return false;
                    }
                }
                if (x - 1 + xi < battlefield.length && x - 1 + xi >= 0){
                    if (battlefield[x - 1 + xi][y + yi]!=0){
                        return false;
                    }
                }
                if (y + 1 + yi < battlefield.length && y + 1 + yi >= 0){
                    if (battlefield[x + xi][y + 1 + yi]!=0){
                        return false;
                    }
                }
                if (y - 1 + yi < battlefield.length && y - 1 + yi >= 0){
                    if (battlefield[x + xi][y - 1 + yi]!=0){
                        return false;
                    }
                }
            }
            deck--;
        }
        return true;
    }
}
