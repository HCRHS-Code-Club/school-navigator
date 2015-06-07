import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int time = Integer.parseInt(args[0]);
        Manager manager = new Manager();
        new Thread(manager).start();

        System.out.println("Press any key to exit");
        Thread.sleep(time);
        System.out.println("\nStopping...");

        manager.stop();
        System.out.println("\n\n");
        System.out.printf("Successes: %d\nFailures: %d\n", manager.getTotalSuccesses(), manager.getTotalFailures());
        System.exit(0);
    }
}
