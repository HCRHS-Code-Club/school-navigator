package SchoolNavigator;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

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

    public static void computePaths(Intersection source)
    {
        source.minDistance = 0.;
        PriorityQueue<Intersection> IntersectionQueue = new PriorityQueue<Intersection>();
        IntersectionQueue.add(source);

        while (!IntersectionQueue.isEmpty()) {
            Intersection u = IntersectionQueue.poll();

            // Visit each Hallway exiting u
            for (Hallway e : u.getAllHallways())
            {
                Intersection i = e.getOtherEnd(u.id);
                double weight = e.length;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < i.minDistance) {
                    IntersectionQueue.remove(i);
                    i.minDistance = distanceThroughU ;
                    i.previous = u;
                    IntersectionQueue.add(i);
                }
            }
        }
    }

    public static List<Intersection> getShortestPathTo(Intersection target)
    {
        List<Intersection> path = new ArrayList<Intersection>();
        for (Intersection Intersection = target; Intersection != null; Intersection = Intersection.previous)
            path.add(Intersection);
        Collections.reverse(path);
        return path;
    }
}
