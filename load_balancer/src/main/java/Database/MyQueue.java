package Database;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue implements DatabaseQueue{

    private Queue<DatabaseAction> actionQueue = new LinkedList<>();

    @Override
    public void add(DatabaseAction action) {
        actionQueue.add(action);
    }
}
