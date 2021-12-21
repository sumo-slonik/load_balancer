package pl.agh.dp.loadbalancer.LoadBalancer;

public interface LoadBalancerInterface {

    void newQuery(String query);
    void setBalanceStrategy(boolean useMinLoadBalance);
}
