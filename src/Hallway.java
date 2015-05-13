
public class Hallway
{
    public int id;

    private Intersection entrance, exit;

    private Room[] rooms;

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
}
