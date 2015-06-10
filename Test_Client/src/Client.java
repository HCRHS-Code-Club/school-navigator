import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by bgoldberg on 6/1/2015.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        Scanner scanner = new Scanner(System.in);
        String input, output;
        while (true) {
            try {
                System.out.print("Enter start room: ");
                input = scanner.nextLine();
                System.out.print("Enter end room: ");
                output = scanner.nextLine();
            } catch(NoSuchElementException e){
                return;
            }
            if(input.equals(""))
                break;
            Socket s = new Socket(serverAddress, 1234);
            System.out.printf("Connected to: %s\n", s.getLocalAddress());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            out.printf("%s,%s\n", input, output);
            System.out.println("Sent: 100,918");
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String answer = "";
            String raw = "";
            while (true) {
                raw = in.readLine();
                if (raw.equals("END"))
                    break;
                answer += raw + "\n";
            }
            System.out.printf("Received: %s\n", answer);
        }
        System.exit(0);
    }

}
