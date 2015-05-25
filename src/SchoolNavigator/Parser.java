package SchoolNavigator;

import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

//http://www.tutorialspoint.com/java_xml/java_jdom_parse_document.htm

public class Parser {
    public static void parse(List<Hallway> hallways, List<Intersection> intersections) {
        try {
            File inputFile = new File("map.xml");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            Element classElement = document.getRootElement();
            List<Element> elementList = classElement.getChildren();
            Hallway tempHallway = new Hallway();
            Intersection tempIntersection = new Intersection();
            Room tempRoom = new Room();
            List<Room> tempRooms = new ArrayList<Room>();
            for(Element element : elementList) {
                //System.out.printf("%s, ", element.getName());
                if(element.getName().equals("Hallway")) {
                    tempHallway.id = element.getAttribute("id").getIntValue();
                    tempHallway.entranceId = element.getAttribute("entrance").getIntValue();
                    tempHallway.exitId = element.getAttribute("exit").getIntValue();
                    for(Element roomElement: element.getChildren()) {
                        if(element.getName().equals("Room")) {
                            tempRoom.roomNumber = roomElement.getAttributeValue("roomNumber");
                            tempRooms.add(tempRoom);
                        }
                    }
                    tempHallway.rooms = tempRooms.toArray(new Room[tempRooms.size()]);
                    hallways.add(new Hallway(tempHallway));
                } else if(element.getName().equals("Intersection")) {
                    tempIntersection.id = element.getAttribute("id").getIntValue();
                    String[] paths = element.getAttributeValue("paths").split(",");
                    for(int i = 0; i < paths.length; i++) {
                        try {
                            tempIntersection.pathIds[i] = Integer.parseInt(paths[i]);
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

}
