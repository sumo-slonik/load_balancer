package pl.agh.dp.loadbalancer.data.acces.domain;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClubRepository extends JpaRepository<Club, Integer> {
    List<Club> findAll();
}