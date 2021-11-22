public class RoundRobin implements ScheduleAlgorithm{

    private Integer currentIndex;

    private Integer currentDatabaseCount;

    private Integer maxDatabaseCount;

    public RoundRobin(){
        this(10);
    }

    public RoundRobin(Integer maxDatabaseCount){
        currentIndex = 0;
        this.maxDatabaseCount = maxDatabaseCount;
    }

    public Integer getCurrentIndex(){
        Integer toReturn = currentIndex;
        currentIndex = (currentIndex+1) % currentDatabaseCount;
        return toReturn;
    }


}
