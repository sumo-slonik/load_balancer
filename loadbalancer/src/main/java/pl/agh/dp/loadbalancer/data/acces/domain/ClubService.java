package pl.agh.dp.loadbalancer.data.acces.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Service;


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
