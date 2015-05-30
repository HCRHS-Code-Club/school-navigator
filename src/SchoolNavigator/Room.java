package SchoolNavigator;

public class Room {
    public int id;
    public String roomNumber;
    public Hallway hallway;
    public String face;

    public Room(){}

    public Room(Room room) {
        this.id = room.id;
        this.roomNumber = room.roomNumber;
        this.hallway = room.hallway;
        this.face = room.face;
    }
}
