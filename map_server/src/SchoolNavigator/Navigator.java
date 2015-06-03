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
        Room start = null, end = null;
        Hallway startHall, endHall, sTemp1, sTemp2, eTemp1, eTemp2;
        Intersection dStart, dEnd;
        String input = null, output = null, request[] = null;
        Object raw = null;

        //Parse XML
        SchoolMap.parse(hallways, intersections);

        //Get Input
        while (!stop) {
            try {
                raw = queue[0].take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(raw.toString() != "stop") {
                request = (String[]) raw;
                input = request[0];
                output = request[1];
                //Find rooms
                for (int i = 0; i < hallways.size(); i++) {
                    for (int j = 0; j < hallways.get(i).getRooms().length; j++) {
                        //System.out.printf("%s\n", hallways.get(i).getRooms()[j].roomNumber);
                        if (hallways.get(i).getRooms()[j].roomNumber.equals(input)) {
                            start = hallways.get(i).getRooms()[j];
                        } else if (hallways.get(i).getRooms()[j].roomNumber.equals(output)) {
                            end = hallways.get(i).getRooms()[j];
                        }
                    }
                }

                if (start == null || end == null) {
                    if (start == null)
                        System.out.println("Start room not found");
                    if (end == null)
                        System.out.println("End room not found");
                    return;
                }

                //Setup
                startHall = start.hallway;
                endHall = end.hallway;
                dStart = new Intersection(20);
                dEnd = new Intersection(21);
                sTemp1 = new Hallway(startHall.getEntrance(), dStart, startHall.direction);
                sTemp2 = new Hallway(dStart, startHall.getExit(), startHall.direction);
                eTemp1 = new Hallway(endHall.getEntrance(), dEnd, endHall.direction);
                eTemp2 = new Hallway(dEnd, endHall.getExit(), endHall.direction);
                startHall.getEntrance().replaceHallway(startHall, sTemp1);
                startHall.getExit().replaceHallway(startHall, sTemp2);
                endHall.getEntrance().replaceHallway(endHall, eTemp1);
                endHall.getExit().replaceHallway(endHall, eTemp2);
                dStart.setHallways(new ArrayList<Hallway>(Arrays.asList(sTemp1, sTemp2)));
                dEnd.setHallways(new ArrayList<Hallway>(Arrays.asList(eTemp1, eTemp2)));

                //Find Path
                SchoolMap.computePaths(dStart);
                //System.out.printf("Distance to %d : %.1f\n",dEnd.id, dEnd.minDistance);
                List<Intersection> path = SchoolMap.getShortestPathTo(dEnd);
                /*System.out.print("Path: ");
                for (Intersection k : path)
                    System.out.print(k.id + ", ");
                System.out.println();*/
                String directions = SchoolMap.getDirections(path, startHall, endHall, start, end);
                try {
                    queue[1].put(directions);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Reset
                sTemp1.getEntrance().replaceHallway(sTemp1, startHall);
                sTemp2.getExit().replaceHallway(sTemp2, startHall);
                eTemp1.getEntrance().replaceHallway(eTemp1, endHall);
                eTemp2.getExit().replaceHallway(eTemp2, endHall);
                for (Intersection intersection : intersections) {
                    intersection.previous = null;
                    intersection.minDistance = Double.POSITIVE_INFINITY;
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
