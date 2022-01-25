package pl.agh.dp.loadbalancer.Connection;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.agh.dp.loadbalancer.ClubPackage.Club;
import pl.agh.dp.loadbalancer.ClubPackage.ClubRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    public List<Club> getAllClubsDetails()
    {
        try
        {
            return clubRepository.findAll();
        }catch (RuntimeException ex)
        {
            throw new RuntimeException("Could not find all clubs");
        }
    }
}
