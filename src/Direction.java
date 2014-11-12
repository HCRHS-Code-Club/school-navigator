
public enum Direction
{
	EAST(0,  0),
	NORTH(1, 0.5 * Math.PI),
	WEST(2,        Math.PI),
	SOUTH(3, 1.5 * Math.PI),
	//In our 2D view, up and down don't have angle components.
	UP(4), 
	DOWN(5);
	
	//The six joints that an interesction may have:
	public static final Direction[] directions = new Direction[] {EAST, NORTH, WEST, SOUTH, UP, DOWN};
	
	private double angle;
	private int id;

	private Direction(int id)
	{
		this.id =  id;
	}
	
	private Direction(int id, double angle)
	{
		this.id =  id;
		this.angle = angle;
	}
	
	public int getId()
	{
		return id;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public static Direction getDireciton(int id)
	{
		return directions[id];
	}
	
	public static Direction getNearestDirection(double angle)
	{
		//4 cardinal directions are in intervals of π/2.
		return directions[(int) Math.round((angle / (0.5 * Math.PI))) % 4];
	}
}
