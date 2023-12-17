import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Await {
    public static Object GetObject(ObjectInputStream ois) {
        try{
            return ois.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
