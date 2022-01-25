package pl.agh.dp.loadbalancer.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.DataBasesInterface.DatabasesInterface;

import java.util.Iterator;
import java.util.List;

@Controller // This means that this class is a Controller
@RequestMapping(path="/clubs") // This means URL's start with /demo (after Application path)
@ComponentScan("pl/agh/dp/loadbalancer/DataBasesInterface")
public class Controler {
    @Autowired
    private ClubService clubService;

    @Autowired
    private DatabasesInterface databasesInterface;



    @GetMapping(path="/getAll")
    public @ResponseBody Iterable<Club> getAllUsers() {
        // This returns a JSON or XML with the users
        return clubService.getAllClubsDetails();
    }

    @GetMapping(path="/getClubs")
    public @ResponseBody
    Iterator<Club> getClubs()
    {
        List<Club> result =  databasesInterface.executeSelect("cos");
        return result.iterator();
    }

    @GetMapping(path="/test")
    public @ResponseBody String test() {
        // This returns a JSON or XML with the users
        return "result";
    }


}