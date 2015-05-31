package SchoolNavigator;

import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Server server = new Server(8082);
        new Thread(server).start();

        System.out.println("Press any key to exit");
        scanner.nextLine();
        System.out.println("Stopping Server");

        server.stop();
        scanner.close();
        return;
    }

}
