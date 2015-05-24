package SchoolNavigator;

import java.util.List;

public class TestRunner extends SchoolMap {


    public static void main(String[] args) {
        Hallway h1 = new Hallway(1, new Room[]{new Room(100)});
        Hallway h2 = new Hallway(2);
        Hallway h3 = new Hallway(3);
        Hallway h4 = new Hallway(4);
        Hallway h5 = new Hallway(5);
        Hallway h6 = new Hallway(6);
        Hallway h7 = new Hallway(7);
        Hallway h8 = new Hallway(8);
        Hallway h9 = new Hallway(9, new Room[]{new Room(919)});

        Intersection i1 = new Intersection(1, new Hallway[]{h1});
        Intersection i2 = new Intersection(2, new Hallway[]{h2, h9});
        Intersection i3 = new Intersection(3, new Hallway[]{h1, h2, h3, h5});
        Intersection i4 = new Intersection(4, new Hallway[]{h3});
        Intersection i5 = new Intersection(5, new Hallway[]{h4, h9});
        Intersection i6 = new Intersection(6, new Hallway[]{h4, h5, h6, h7});
        Intersection i7 = new Intersection(7, new Hallway[]{h6});
        Intersection i8 = new Intersection(8, new Hallway[]{h7, h8});
        Intersection i9 = new Intersection(9, new Hallway[]{h8});

        h1.setUp(i3, i1);
        h2.setUp(i2, i3);
        h3.setUp(i3, i4);
        h4.setUp(i5, i6);
        h5.setUp(i6, i3);
        h6.setUp(i6, i7);
        h7.setUp(i8, i6);
        h8.setUp(i9, i8);
        h9.setUp(i5, i2);

        h4.length = .5;

        Room start = h1.getRooms()[0];
        Room end = h9.getRooms()[0];
        Hallway startHall = start.hallway;
        Hallway endHall = end.hallway;
        Intersection dStart = new Intersection();
        Intersection dEnd = new Intersection();
        Hallway sTemp1 = new Hallway(startHall.getEntrance(), dStart);
        Hallway sTemp2 = new Hallway(dStart, startHall.getExit());
        Hallway eTemp1 = new Hallway(endHall.getEntrance(), dEnd);
        Hallway eTemp2 = new Hallway(dEnd, endHall.getExit());
        startHall.getEntrance().replaceHallway(startHall, sTemp1);
        startHall.getExit().replaceHallway(startHall, sTemp2);
        endHall.getEntrance().replaceHallway(endHall, eTemp1);
        endHall.getExit().replaceHallway(endHall, eTemp2);
        dStart.setHallways(new Hallway[]{sTemp1, sTemp2});
        dEnd.setHallways(new Hallway[]{eTemp1, eTemp2});

        computePaths(dStart);
        System.out.println("Distance to " + start.id + ": " + dEnd.minDistance);
        List<Intersection> path = getShortestPathTo(dEnd);
        System.out.print("Path: ");
        for (Intersection j : path)
            System.out.print(j.id + ", ");
        System.out.println("");
    }
}
