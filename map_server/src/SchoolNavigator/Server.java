package SchoolNavigator;

import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server implements Runnable {

    private int port = 8080;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private Thread runningThread = null;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    private BlockingQueue[][] queue;
    private Navigator[] navigators;
    private RoundRobin roundRobin;

    public Server() {
        try {
            Config.load();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            System.err.println("Thread or port value not a number");
            System.err.println("Invalid config file");
            System.exit(-1);
        }
        port = Config.port;
        queue = new LinkedBlockingQueue[Config.threads][2];
        navigators = new Navigator[Config.threads];
        roundRobin = new RoundRobin(Config.threads);
        File inputFile = new File(Config.mapfile);
        if(!inputFile.exists()) {
            System.err.println("Map file not found");
            System.err.println("Invalid config file");
            System.exit(-1);
        }
        for(int i = 0; i < navigators.length; i++) {
            queue[i][0] = new LinkedBlockingQueue();
            queue[i][1] = new LinkedBlockingQueue();
            navigators[i] = new Navigator(queue[i]);
            new Thread(navigators[i]).start();
        }
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute(new Worker(clientSocket, queue, roundRobin));
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
            for(int i = 0; i < navigators.length; i++) {
                navigators[i].stop();
            }
            for(int i = 0; i < queue.length; i++) {
                try {
                    queue[i][0].put("stop");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            //this.serverSocket.setSoTimeout(10000);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}
