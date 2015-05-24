package SchoolNavigator;

public class Room
{
    public int id;
    public String roomNumber;
    public Hallway hallway;

    public Room(){}
    public Room(int id) {
        this.id = id;
        roomNumber = Integer.toString(id);
    }
    public Room(int id, String roomNumber) {
        this.id = id;
        this.roomNumber = roomNumber;
    }

    public Room(int id, String roomNumber, Hallway hallway) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.hallway = hallway;
    }
}
