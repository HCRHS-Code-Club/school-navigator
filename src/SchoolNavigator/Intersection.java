package SchoolNavigator;

import java.util.ArrayList;
import java.util.List;

public class Intersection implements Comparable<Intersection> {
    public int id;
    public List<Integer> pathIds;
    public List<Hallway> paths;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Intersection previous;
    boolean altered = false;

    public Intersection(){
        paths = new ArrayList<Hallway>();
        pathIds = new ArrayList<Integer>();
    }
    public Intersection(int id) {
        paths = new ArrayList<Hallway>();
        pathIds = new ArrayList<Integer>();
        this.id = id;
    }
    public Intersection(int id, List<Hallway> paths) {
        paths = new ArrayList<Hallway>();
        pathIds = new ArrayList<Integer>();
        this.id = id;
        this.paths = paths;
    }

    public Intersection(Intersection intersection) {
        this.id = intersection.id;
        this.paths = intersection.paths;
        this.pathIds = intersection.pathIds;
        this.minDistance = intersection.minDistance;
        this.previous = intersection.previous;
        this.altered = intersection.altered;
    }

    public List<Hallway> getAllHallways() {
        return paths;
    }
    public void replaceHallway(Hallway oldHall, Hallway newHall) {
        for(int i = 0; i < paths.size(); i++) {
            if(paths.get(i).equals(oldHall)) {
                paths.set(i, newHall);
            }
        }

    }
    public void setHallways(List<Hallway> hallways) {
        paths = hallways;
    }
    public int compareTo(Intersection other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
