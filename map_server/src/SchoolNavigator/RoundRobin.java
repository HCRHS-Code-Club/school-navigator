package SchoolNavigator;

public class RoundRobin {
    private int index = 0;
    private final int top;

    public RoundRobin(int top) {
        this.top = top;
    }

    public int getNext() {
        index++;
        if (index == top)
            index = 0;
        return index;
    }

}
