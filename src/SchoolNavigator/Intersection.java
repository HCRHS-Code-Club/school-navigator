package SchoolNavigator;

import java.util.ArrayList;
import java.util.List;

public class Intersection implements Comparable<Intersection> {
    public int id;
    public List<Integer> pathIds;
    public List<Hallway> paths;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Intersection previous;

    public Intersection(){
        paths = new ArrayList<Hallway>();
        pathIds = new ArrayList<Integer>();
    }
    public Intersection(int id) {
        paths = new ArrayList<Hallway>();
        pathIds = new ArrayList<Integer>();
        this.id = id;
    }
    public Intersection(Intersection intersection) {
        this.id = intersection.id;
        this.paths = intersection.paths;
        this.pathIds = intersection.pathIds;
        this.minDistance = intersection.minDistance;
        this.previous = intersection.previous;
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
    public Hallway connecting(Intersection otherEnd) {
        for(Hallway i : this.paths) {
            for(Hallway j : otherEnd.paths) {
                if(i.equals(j)) {
                    return i;
                }
            }
        }
        return null;
    }
    public void setHallways(List<Hallway> hallways) {
        paths = hallways;
    }
    public int compareTo(Intersection other)
    {
        return Double.compare(minDistance, other.minDistance);
    }
}
