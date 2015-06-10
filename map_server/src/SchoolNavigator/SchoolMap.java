package SchoolNavigator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

public class SchoolMap
{
    public static void computePaths(Intersection source)
    {
        source.minDistance = 0.;
        PriorityQueue<Intersection> IntersectionQueue = new PriorityQueue<Intersection>();
        IntersectionQueue.add(source);

        while (!IntersectionQueue.isEmpty()) {
            Intersection u = IntersectionQueue.poll();

            // Visit each Hallway exiting u
            for (Hallway e : u.getAllHallways())
            {
                Intersection i = e.getOtherEnd(u);
                if(i == null)
                    System.out.println("Data Mismatch");
                double weight = e.length;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < i.minDistance) {
                    IntersectionQueue.remove(i);
                    i.minDistance = distanceThroughU ;
                    i.previous = u;
                    IntersectionQueue.add(i);
                }
            }
        }
    }

    public static List<Intersection> getShortestPathTo(Intersection target)
    {
        List<Intersection> path = new ArrayList<Intersection>();
        for (Intersection Intersection = target; Intersection != null; Intersection = Intersection.previous)
            path.add(Intersection);
        Collections.reverse(path);
        return path;
    }

    public static void parse(List<Hallway> hallways, List<Intersection> intersections) {
        try {
            File inputFile = new File("map.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();
            List<Element> elementList = classElement.getChildren();
            for(Element element : elementList) {
                Hallway tempHallway = new Hallway();
                Intersection tempIntersection = new Intersection();
                Room tempRoom = new Room();
                List<Room> tempRooms = new ArrayList<Room>();
                if(element.getName().equals("Hallway")) {
                    tempHallway.id = element.getAttribute("id").getIntValue();
                    tempHallway.entranceId = element.getAttribute("entrance").getIntValue();
                    tempHallway.exitId = element.getAttribute("exit").getIntValue();
                    tempHallway.direction = element.getAttributeValue("direction");
                    for(Element roomElement: element.getChildren()) {
                        if(roomElement.getName().equals("Room")) {
                            tempRoom.roomNumber = roomElement.getAttributeValue("roomNumber");
                            tempRoom.face = roomElement.getAttributeValue("face");
                            tempRooms.add(new Room(tempRoom));
                        }
                    }
                    tempHallway.rooms = tempRooms.toArray(new Room[tempRooms.size()]);
                    hallways.add(new Hallway(tempHallway));
                } else if(element.getName().equals("Intersection")) {
                    tempIntersection.id = element.getAttribute("id").getIntValue();
                    String[] paths = element.getAttributeValue("paths").split(",");
                    for(int i = 0; i < paths.length; i++) {
                        try {
                            tempIntersection.pathIds.add(Integer.parseInt(paths[i]));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    intersections.add(new Intersection(tempIntersection));
                }
            }
            for(Hallway hallway : hallways) {
                for(Intersection intersection: intersections) {
                    if(hallway.entranceId == intersection.id) {
                        hallway.entrance = intersection;
                    } else if(hallway.exitId == intersection.id) {
                        hallway.exit = intersection;
                    }
                    for(int pathId : intersection.pathIds) {
                        if(pathId == hallway.id) {
                            intersection.paths.add(hallway);
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (org.jdom2.JDOMException e){
            e.printStackTrace();
        }
    }

    public static String getDirections(List<Intersection> path, Hallway startHall, Hallway endHall, Room start, Room end) {
        final String left = "left", right = "right", strait = "strait";
        String directions = "\nExit the room and turn ";
        if(startHall.direction.equals("ew") && start.face.equals("n") && startHall.getEntrance().equals(path.get(1))) {
            directions += right;
        } else if(startHall.direction.equals("ew") && start.face.equals("n") && startHall.getExit().equals(path.get(1))) {
            directions += left;
        } else if(startHall.direction.equals("ew") && start.face.equals("s") && startHall.getEntrance().equals(path.get(1))) {
            directions += left;
        } else if(startHall.direction.equals("ew") && start.face.equals("s") && startHall.getExit().equals(path.get(1))) {
            directions += right;
        } else if(startHall.direction.equals("ns") && start.face.equals("e") && startHall.getEntrance().equals(path.get(1))) {
            directions += right;
        } else if(startHall.direction.equals("ns") && start.face.equals("e") && startHall.getExit().equals(path.get(1))) {
            directions += left;
        } else if(startHall.direction.equals("ns") && start.face.equals("w") && startHall.getEntrance().equals(path.get(1))) {
            directions += left;
        } else if(startHall.direction.equals("ns") && start.face.equals("w") && startHall.getExit().equals(path.get(1))) {
            directions += right;
        }
        directions += "\n";
        if(path.size() > 2) {
            for (int i = 0; i + 2 < path.size(); i++) {
                directions += "Turn ";
                Hallway current = path.get(i).connecting(path.get(i + 1));
                Hallway next = path.get(i + 1).connecting(path.get(i + 2));
                if (current.direction.equals(next.direction)) {
                    directions += strait;
                } else if (current.direction.equals("ew")) {
                    if (current.getEntrance().equals(path.get(i + 1)) && next.getEntrance().equals(path.get(i + 1))) {
                        directions += left;
                    } else if (current.getExit().equals(path.get(i + 1)) && next.getEntrance().equals(path.get(i + 1))) {
                        directions += right;
                    } else if (current.getEntrance().equals(path.get(i + 1)) && next.getExit().equals(path.get(i + 1))) {
                        directions += right;
                    } else if (current.getExit().equals(path.get(i + 1)) && next.getExit().equals(path.get(i + 1))) {
                        directions += left;
                    }
                } else {
                    if (current.getEntrance().equals(path.get(i + 1)) && next.getEntrance().equals(path.get(i + 1))) {
                        directions += right;
                    } else if (current.getExit().equals(path.get(i + 1)) && next.getEntrance().equals(path.get(i + 1))) {
                        directions += left;
                    } else if (current.getEntrance().equals(path.get(i + 1)) && next.getExit().equals(path.get(i + 1))) {
                        directions += left;
                    } else if (current.getExit().equals(path.get(i + 1)) && next.getExit().equals(path.get(i + 1))) {
                        directions += right;
                    }
                }
                directions += "\n";
            }
        }
        directions += "Turn ";
        if(endHall.direction.equals("ew") && end.face.equals("n") && endHall.getEntrance().equals(path.get(path.size()-2))) {
            directions += left;
        } else if(endHall.direction.equals("ew") && end.face.equals("n") && endHall.getExit().equals(path.get(path.size()-2))) {
            directions += right;
        } else if(endHall.direction.equals("ew") && end.face.equals("s") && endHall.getEntrance().equals(path.get(path.size()-2))) {
            directions += right;
        } else if(endHall.direction.equals("ew") && end.face.equals("s") && endHall.getExit().equals(path.get(path.size()-2))) {
            directions += left;
        } else if(endHall.direction.equals("ns") && end.face.equals("e") && endHall.getEntrance().equals(path.get(path.size()-2))) {
            directions += left;
        } else if(endHall.direction.equals("ns") && end.face.equals("e") && endHall.getExit().equals(path.get(path.size()-2))) {
            directions += right;
        } else if(endHall.direction.equals("ns") && end.face.equals("w") && endHall.getEntrance().equals(path.get(path.size()-2))) {
            directions += right;
        } else if(endHall.direction.equals("ns") && end.face.equals("w") && endHall.getExit().equals(path.get(path.size()-2))) {
            directions += left;
        }
        directions += " into the room\n";
        return directions;
    }
}
