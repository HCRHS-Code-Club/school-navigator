package SchoolNavigator;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable{

    protected Socket clientSocket = null;
    private BlockingQueue[][] queue = null;
    private RoundRobin roundRobin = null;

    public Worker(Socket clientSocket, BlockingQueue[][] queue, RoundRobin roundRobin) {
        this.clientSocket = clientSocket;
        this.queue = queue;
        this.roundRobin = roundRobin;

    }

    public void run() {
        try {
            BufferedReader input  = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
            String raw = null;
            String[] request = null;
            String response = null;
            int queueNum = roundRobin.getNext();
            raw = input.readLine();
            System.out.printf("Received: %s\n", raw);
            request = raw.split(",");
            try {
                do {
                    queue[queueNum][0].put(request);
                    response = (String) queue[queueNum][1].take();
                    System.out.printf("Message: %s\n", response);
                } while (response.equals("") || response == null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Sending: %s\n", response);
            output.print(response + "END");
            System.out.printf("Sent: %s\n", response);

            output.flush();
            output.close();
            input.close();
            clientSocket.close();

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
