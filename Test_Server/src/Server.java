import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by bgoldberg on 6/1/2015.
 */
public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket listener = new ServerSocket(8080);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String answer = input.readLine();
                    Thread.sleep(5000);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(answer);
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }

}
