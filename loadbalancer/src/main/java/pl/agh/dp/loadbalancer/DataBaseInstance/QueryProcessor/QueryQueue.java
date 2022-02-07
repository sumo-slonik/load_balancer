package pl.agh.dp.loadbalancer.DataBaseInstance.QueryProcessor;

public interface QueryQueue<T> {

    T get();

    void add(T element);

    Boolean isEmpty();

}
