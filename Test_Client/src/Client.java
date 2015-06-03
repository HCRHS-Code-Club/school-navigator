import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

/**
 * Created by bgoldberg on 6/1/2015.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        Socket s = new Socket(serverAddress, 8080);
        System.out.printf("Connected to: %s\n", s.getLocalAddress());
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        out.println("100,918");
        System.out.println("Sent: 100,918");
        BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String answer = "";
        String raw = "";
        while (true) {
            raw = input.readLine();
            if(raw.equals("END"))
                break;
            answer += raw + "\n";
        }
        System.out.printf("Received: %s\n", answer);
        System.exit(0);
    }

}
