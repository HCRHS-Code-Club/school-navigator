import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if(args.length == 1 || args.length == 2) {
            Manager manager;
            long pings = Long.parseLong(args[0]), startTime, endTime;
            int threads;
            if(args.length == 2)
                threads = Integer.parseInt(args[1]);
            else
                threads = 1;
            manager = new Manager(pings, threads);
            new Thread(manager).start();
            startTime = System.currentTimeMillis();
            while (!manager.isStopped()){}
            endTime = System.currentTimeMillis();
            //manager.stop();
            System.out.println("\n\n");
            System.out.printf("Time taken (milliseconds): %d\nSuccesses: %d\nFailures: %d\n", manager.getTotalSuccesses(), manager.getTotalFailures(), endTime-startTime);
            System.exit(0);
        } else {
            System.out.println("One or two arugments must be specified");
            System.exit(-1);
        }





    }
}
