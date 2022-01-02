package pl.agh.dp.loadbalancer.data.acces.domain.infra.controler;


import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.agh.dp.loadbalancer.data.acces.domain.Club;
import pl.agh.dp.loadbalancer.data.acces.domain.ClubService;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBases;
import pl.agh.dp.loadbalancer.data.acces.domain.infra.datasource.DataBasesContextHolder;

@RestController
@RequiredArgsConstructor
public class DetailsController {
    private final ClubService clubService;
    private final DataBasesContextHolder dataSourceContextHolder;
    @GetMapping(value="/getEmployeeDetails/{dataSourceType}" , produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Club> getAllEmployeeDetails(@PathVariable("dataSourceType") String dataSourceType){
        System.out.println(dataSourceType);
        if(DataBases.FIRST.toString().equals(dataSourceType)){
            dataSourceContextHolder.setBranchContext(DataBases.FIRST);

        }else{
            dataSourceContextHolder.setBranchContext(DataBases.SECOND);
        }
        return clubService.getAllClubsDetails();
    }
}
