import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker  implements Runnable{

    private boolean stopped = false;
    private long oldTime;
    private long time;
    private long pings = 0;
    private long successes = 0;
    private long failures = 0;
    @Override
    public void run()  {
        time = System.currentTimeMillis();
        oldTime = time;
        while (!isStopped()) {
            time = System.currentTimeMillis();
            if(time - oldTime <= 1000) {
                try {
                    String serverAddress = "localhost";
                    Socket s = new Socket(serverAddress, 1234);
                    //System.out.printf("Connected to: %s\n", s.getLocalAddress());
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    out.println("100,918");
                    //System.out.println("Sent: 100,918");
                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String answer = "";
                    String raw = "";
                    while (true) {
                        raw = input.readLine();
                        if (raw.equals("END"))
                            break;
                        answer += raw + "\n";
                    }
                    //System.out.printf("Received: %s\n", answer);
                    pings++;
                    if(answer.equals("\nExit the room and turn right\nTurn right\nTurn left\nTurn right into the room\n"))
                        successes++;
                    else
                        failures++;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    oldTime = time;
                }
            }
        }
    }

    public synchronized void stop() {
        stopped = true;
    }

    public synchronized boolean isStopped() {
        return stopped;
    }

    public synchronized long getPings() {
        return pings;
    }

    public synchronized long getSuccesses() {
        return successes;
    }

    public synchronized long getFailures() {
        return failures;
    }
}
