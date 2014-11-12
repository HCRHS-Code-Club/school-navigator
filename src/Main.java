
public class Main
{
	public static void main(String[] args)
	{
		//Rudimentary testing of basic functions:
		directionTest(Math.PI / 3);
		directionTest(Math.PI);
		directionTest(1.4*Math.PI);
	}

	private static void directionTest(double d)
	{
		System.out.println("Nearest to " + d + " (" + d*180/Math.PI + " degrees) is " + Direction.getNearestDirection(d));
	}
}
