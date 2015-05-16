package SchoolNavigator;

public class Intersection implements Comparable<Intersection> {
    public int id;
    private Hallway[] paths;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Intersection previous;

    public Intersection(){}
    public Intersection(int id, Hallway[] paths) {
        this.id = id;
        this.paths = paths;
    }
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
    public int compareTo(Intersection other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
