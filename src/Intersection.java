import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class Intersection
{
	private Hallway[] halls;
	private Point2D location;
	
	public Intersection()
	{
		
	}
	
	public Hallway getHall(int direction)
	{
		return halls[direction];
	}
	
	public void setHall(Hallway hall, int direction)
	{
		halls[direction] = hall;
	}
	
	public Map<Direction, Hallway> getHalls()
	{
		Map<Direction, Hallway> halls = new HashMap<Direction, Hallway>();
		for (Direction direction : Direction.directions)
			if (this.halls[direction.getId()] != null)
				halls.put(direction, this.halls[direction.getId()]);
		return halls;
	}
	
	public Point2D getLocation()
	{
		return location;
	}
	
	public void setLocation(Point2D location)
	{
		this.location = location;
	}
}