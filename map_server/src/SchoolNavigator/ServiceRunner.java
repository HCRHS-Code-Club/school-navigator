package SchoolNavigator;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;

public class ServiceRunner implements Daemon{
    private static Server server;

    public static void main(String[] args) {
        server = new Server();
        new Thread(server).start();
    }

    @Override
    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        System.out.println("initializing ...");
    }

    @Override
    public void start() throws Exception {
        System.out.println("starting ...");
        main(null);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stopping ...");
        if (server != null) {
            server.stop();
        }
    }

    @Override
    public void destroy() {
        System.out.println("done.");
        server = null;
    }
}
