package pl.agh.dp.loadbalancer.ClubPackage;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClubRepository extends JpaRepository<Club, Integer> {
    List<Club> findAll();
}