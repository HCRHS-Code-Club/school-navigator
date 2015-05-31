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
            DataInputStream input  = new DataInputStream(clientSocket.getInputStream());
            OutputStream output = clientSocket.getOutputStream();
            String raw = null;
            String[] request = null;
            String response = null;
            int queueNum = roundRobin.getNext();

            while (clientSocket.isConnected()) {
                if((raw = input.readUTF()) != null && raw.length() > 0) {
                    request = raw.split(",");
                    try {
                        queue[queueNum][0].put(request);
                        response = (String) queue[queueNum][1].take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output.write(response.getBytes());
                }
            }
            output.close();
            input.close();

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
