package SchoolNavigator;

public class Room {
    public int id;
    public String roomNumber;
    public Hallway hallway;
    public String face;

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
    public Room(Room room) {
        this.id = room.id;
        this.roomNumber = room.roomNumber;
        this.hallway = room.hallway;
        this.face = room.face;
    }
}
