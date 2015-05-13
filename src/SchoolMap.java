import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SchoolMap
{
    private Hallway[] hallways;
    private List<Intersection> path;

    void save(File file) {}

    void load(File file) {}

    Hallway getHallwayByID(int id) {
        for(Hallway hallway : hallways) {
            if(hallway.id == id)
                return hallway;
        }
        return null;
    }

    void getRootHallway() {}

    void find(Intersection start, Intersection end) {
        path = new ArrayList<Intersection>();
        search(start, end, new ArrayList<Integer>());
    }

    boolean search(Intersection current, Intersection end, List<Integer> visited) {
        if(current.id == end.id) {
            return true;
        } else if(inList(visited, current.id)) {
            return false;
        } else {
            visited.add(current.id);
            for(Hallway hallway: current.getAllHallways()) {
                if (hallway.getEntrance().equals(current)) {
                    if(search(hallway.getExit(), end, visited)) {
                        path.add(current);
                        return true;
                    }
                } else {
                    if(search(hallway.getEntrance(), end, visited)) {
                        path.add(current);
                        return true;
                    }
                }
            }
            return false;
        }

    }

    boolean inList(List<Integer> list, int find) {
        for(int i : list) {
            if(i == find)
                return true;
        }
        return false;
    }
}
