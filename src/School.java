import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class School
{
	private List<Room> rooms;
	private List<Hallway> hallways;
	private Hallway origin;
	
	public School(Hallway[] halls)
	{
		//Populates private List<Hallway> hallways with the given hallways and private List<Room> rooms with the
		//Rooms in the given hallways/
		hallways = new ArrayList<Hallway>();
		rooms = new ArrayList<Room>();
		for (Hallway hall : halls)
		{
			hallways.add(hall);
			for (Room room : hall.getRooms())
				rooms.add(room);
		}
		
		//Chooses the first hall as the origin.
		origin = halls[0];
		origin.getStart().setLocation(new Point(0, 0));
		//Start the recursive coordinate-setter.
		setUpCoordinates(origin.getStart());
	}

	//Assigns each intersection some geometric coordinates based on the intersection of origin.
	//TODO: major testing. Contains recursion.
	private void setUpCoordinates(Intersection intersection)
	{
		Map<Direction, Hallway> map = intersection.getHalls();
		Set<Direction> directions = map.keySet();
		for (Direction direction: directions)
		{
			if (map.get(direction).getEnd().getLocation() != null)
				continue; //Don't repeat back and forth!
			
			double x = intersection.getLocation().getX();
			double y = intersection.getLocation().getY();
			double len = map.get(direction).getLength();
			if (direction == Direction.EAST)
				x += len;
			else if (direction == Direction.NORTH)
				y += len;
			else if (direction == Direction.WEST)
				x -= len;
			else if (direction == Direction.SOUTH)
				y -= len;

			map.get(direction).getEnd().setLocation(new Point2D.Double(x, y));
			setUpCoordinates(map.get(direction).getEnd());
		}
	}
}
