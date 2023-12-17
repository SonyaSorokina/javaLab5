import java.io.*;
import java.net.Socket;

public class Async {
    public static void SendObject(Object object, ObjectOutputStream oos) {
        try{
            oos.writeObject(object);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}