package SchoolNavigator;

import java.util.*;
import java.util.concurrent.BlockingQueue;

public class Navigator implements Runnable {

    private boolean stop = false;
    private BlockingQueue[] queue = null;

    public Navigator(BlockingQueue[] queue) {
        this.queue = queue;
    }

    public void run() {

        //Declare vars
        List<Hallway> hallways = new ArrayList<Hallway>();
        List<Intersection> intersections = new ArrayList<Intersection>();
        String input = null, output = null, request[] = null;
        Object raw = null;

        //Parse XML
        SchoolMap.parse(hallways, intersections);

        //Get Input
        while (!isStopped()) {
            try {
                raw = queue[0].take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(raw.toString() != "stop") {
                request = (String[]) raw;
                input = request[0];
                output = request[1];

                String directions = SchoolMap.navigate(input, output, hallways, intersections);

                try {
                    queue[1].put(directions);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        stop = true;
    }

    public boolean isStopped() {
        return stop;
    }
}
