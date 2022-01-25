package pl.agh.dp.loadbalancer.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.ClubPackage.ClubRepository;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

@Controller // This means that this class is a Controller
@RequestMapping(path="/clubs") // This means URL's start with /demo (after Application path)
public class Controler {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private ClubRepository clubRepository;



    @GetMapping(path="/getAll")
    public @ResponseBody Iterable<Club> getAllUsers() {
        // This returns a JSON or XML with the users
        return clubRepository.findAll();
    }

    @GetMapping(path="/test")
    public @ResponseBody String test() {
        // This returns a JSON or XML with the users
        return "result";
    }


}