package SchoolNavigator;

import java.util.*;

public class TestRunner {

    public static void main(String[] args) {

        //Declare vars
        List<Hallway> hallways = new ArrayList<Hallway>();
        List<Intersection> intersections = new ArrayList<Intersection>();
        Scanner scanner = new Scanner(System.in);
        String input, output, directions;

        //Parse XML
        SchoolMap.parse(hallways, intersections);
        while (true) {
            //Get Input
            try {
                System.out.print("Enter start room: ");
                input = scanner.nextLine();
                System.out.print("Enter end room: ");
                output = scanner.nextLine();
            } catch(NoSuchElementException e){
                return;
            }
            if(input.equals(""))
                break;

            directions = SchoolMap.navigate(input, output, hallways, intersections);

            System.out.print(directions);
        }
    }
}
