package SchoolNavigator;

public class Hallway {
    public int id;
    private Intersection entrance, exit;
    private Room[] rooms;
    public double length = 1;

    public Hallway(){}
    public Hallway(int id) {
        this.id = id;
    }
    public Hallway(int id, Intersection entrance, Intersection exit, Room[] rooms, double length) {
        this.id = id;
        this.entrance = entrance;
        this.exit = exit;
        this.rooms = rooms;
        this.length = length;
    }
    public void setUp(Intersection entrance, Intersection exit) {
        this.entrance = entrance;
        this.exit = exit;
    }
    public Intersection getEntrance() {
        return entrance;
    }
    public Intersection getExit() {
        return exit;
    }
    public Room[] getRooms() {
        return rooms;
    }
    public Room getRoomById(int id) {
        for(Room room : rooms) {
            if(room.id == id)
                return room;
        }
        return null;
    }
    public Intersection getOtherEnd(Intersection i) {
        if(entrance.equals(i))
            return exit;
        else if(exit.equals(i))
            return entrance;
        else
            return null;
    }
}
