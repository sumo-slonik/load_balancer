package pl.agh.dp.loadbalancer.data.acces;


import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete


public interface ClubRepository extends CrudRepository<Club, Integer> {

}