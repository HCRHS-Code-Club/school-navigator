
public class Intersection
{
    public int id;
    private Hallway[] paths;
    public Hallway[] getAllHallways() {
        return paths;
    }
    public Hallway getHallway(char direction) {
        switch (direction) {
            case 'n': return paths[0];
            case 'e': return paths[1];
            case 's': return paths[2];
            case 'w': return paths[3];
            default: return null;
        }
    }
}
