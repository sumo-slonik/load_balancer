package Database;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase implements Database {

    private DatabaseState currentState;

    private DatabaseQueue actionToPerform = new MyQueue();

    private DatabaseState state;

}
