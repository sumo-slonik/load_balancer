package pl.agh.dp.loadbalancer.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;
import pl.agh.dp.loadbalancer.LoadBalancer.BalanceStrategy;
import pl.agh.dp.loadbalancer.LoadBalancer.LoadBalancerImpl;
import pl.agh.dp.loadbalancer.LoadBalancer.LoadBalancerInterface;
import pl.agh.dp.loadbalancer.LoadBalancer.LoadMeasurer;
import pl.agh.dp.loadbalancer.LoadBalancer.MinLoadStrategy;
import pl.agh.dp.loadbalancer.LoadBalancer.RoundRobinStrategy;

@Configuration
public class BalanceStrategyConfiguration {
//    load measure

    @Bean
    LoadMeasurer loadMeasurer()
    {
        return new LoadMeasurer();
    }
//    minLoadStrategy
    @Bean
    BalanceStrategy minLoad()
    {
        return new MinLoadStrategy();
    }

//    roundRobin

    @Bean
    BalanceStrategy roundRobin()
    {
        return new RoundRobinStrategy();
    }



//    loadBalancer
    @Bean
    LoadBalancerInterface loadBalancer()
    {
        return new LoadBalancerImpl();
    }
}
