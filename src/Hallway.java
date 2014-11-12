import java.util.List;


public class Hallway
{
	private int id;
	private int hallNumber;
	private static final int[] hallToFloor = new int[] {1, 2, 3, 1, 1, 1, 1, 2, 2};
	//automatically sets the floor based on the known hall numbers. This should probably just be included in the data even though it seems set in stone.
	private Intersection start, end;
	private List<Room> rooms;
	private double length;
	private int floor;
	
	public Hallway(double length, int hallNumber, int id)
	{
		this.id = id;
		this.hallNumber = hallNumber;
		this.floor = hallToFloor[hallNumber];
		this.length = length;
		start = new Intersection();
	}
	
	public Intersection getStart()
	{
		return start;
	}
	
	public Intersection getEnd()
	{
		return end;
	}
	
	public List<Room> getRooms()
	{
		return rooms;
	}
	
	public double getLength()
	{
		return length;
	}
	
	public int getFloor()
	{
		return floor;
	}
	
	public int getHallNumber()
	{
		return hallNumber;
	}
	
	public int getId()
	{
		return id;
	}
}