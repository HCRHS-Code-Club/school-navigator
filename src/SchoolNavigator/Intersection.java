package SchoolNavigator;

public class Intersection implements Comparable<Intersection> {
    public int id;
    private Hallway[] paths;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Intersection previous;
    boolean altered = false;

    public Intersection(){}
    public Intersection(int id) {
        this.id = id;
    }
    public Intersection(int id, Hallway[] paths) {
        this.id = id;
        this.paths = paths;
    }

    public Intersection(Intersection intersection) {
        this.id = intersection.id;
        this.paths = intersection.paths;
        this.minDistance = intersection.minDistance;
        this.previous = intersection.previous;
        this.altered = intersection.altered;
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
    public void replaceHallway(Hallway oldHall, Hallway newHall) {
        for(int i = 0; i < paths.length; i++) {
            if(paths[i].equals(oldHall)) {
                paths[i] = newHall;
            }
        }

    }
    public void setHallways(Hallway[] hallways) {
        paths = hallways;
    }
    public int compareTo(Intersection other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
