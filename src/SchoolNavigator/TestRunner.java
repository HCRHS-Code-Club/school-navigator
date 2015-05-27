package SchoolNavigator;

import java.util.*;

public class TestRunner extends SchoolMap {


    public static void main(String[] args) {

        List<Hallway> hallways = new ArrayList<Hallway>();
        List<Intersection> intersections = new ArrayList<Intersection>();
        Parser.parse(hallways, intersections);
        Scanner scanner = new Scanner(System.in);
        Room start = null, end = null;
        Hallway startHall, endHall, sTemp1, sTemp2, eTemp1, eTemp2;
        Intersection dStart, dEnd;
        String input, output;
        while (true) {
            try {
                System.out.print("Enter start room: ");
                input = scanner.nextLine();
                System.out.print("Enter end room: ");
                output = scanner.nextLine();
            } catch(NoSuchElementException e){
                return;
            }
            if(input.equals(""))
                break;
            else {
                for(int i = 0; i < hallways.size(); i++) {
                    for(int j = 0; j < hallways.get(i).getRooms().length; j++) {
                        //System.out.printf("%s\n", hallways.get(i).getRooms()[j].roomNumber);
                        if(hallways.get(i).getRooms()[j].roomNumber.equals(input)) {
                            start = hallways.get(i).getRooms()[j];
                        } else if(hallways.get(i).getRooms()[j].roomNumber.equals(output)) {
                            end = hallways.get(i).getRooms()[j];
                        }
                    }
                }
            }
            if(start == null || end == null) {
                if(start == null)
                    System.out.println("Start room not found");
                if(end == null)
                    System.out.println("End room not found");
                return;
            }
            //start = hallways.get(0).getRooms()[0];
            //end = hallways.get(7).getRooms()[0];
            startHall = start.hallway;
            endHall = end.hallway;
            dStart = new Intersection(20);
            dEnd = new Intersection(21);
            sTemp1 = new Hallway(startHall.getEntrance(), dStart);
            sTemp2 = new Hallway(dStart, startHall.getExit());
            eTemp1 = new Hallway(endHall.getEntrance(), dEnd);
            eTemp2 = new Hallway(dEnd, endHall.getExit());
            startHall.getEntrance().replaceHallway(startHall, sTemp1);
            startHall.getExit().replaceHallway(startHall, sTemp2);
            endHall.getEntrance().replaceHallway(endHall, eTemp1);
            endHall.getExit().replaceHallway(endHall, eTemp2);
            dStart.setHallways(new ArrayList<Hallway>(Arrays.asList(sTemp1, sTemp2)));
            dEnd.setHallways(new ArrayList<Hallway>(Arrays.asList(eTemp1, eTemp2)));

            computePaths(dStart);
            System.out.printf("Distance to %d : %.1f\n",dEnd.id, dEnd.minDistance);
            List<Intersection> path = getShortestPathTo(dEnd);
            System.out.print("Path: ");
            for (Intersection k : path)
                System.out.print(k.id + ", ");
            System.out.println();

            sTemp1.getEntrance().replaceHallway(sTemp1, startHall);
            sTemp2.getExit().replaceHallway(sTemp2, startHall);
            eTemp1.getEntrance().replaceHallway(eTemp1, endHall);
            eTemp2.getExit().replaceHallway(eTemp2, endHall);
            for(Intersection intersection : intersections) {
                intersection.previous = null;
                intersection.minDistance = Double.POSITIVE_INFINITY;
            }
        }
    }
}
