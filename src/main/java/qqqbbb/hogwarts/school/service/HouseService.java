package qqqbbb.hogwarts.school.service;

import org.springframework.stereotype.Service;
import qqqbbb.hogwarts.school.model.House;
import qqqbbb.hogwarts.school.repository.HouseRepository;
import java.util.*;

@Service
public class HouseService
{
    private final HouseRepository repository;

    public HouseService(HouseRepository repository)
    {
        this.repository = repository;
    }

    public House addHouse(House house)
    {
        return repository.save(house);
    }

    public House getHouse(long id)
    {
        return repository.findById(id).get();
    }

    public House editHouse(House house)
    {
        return repository.save(house);
    }

    public void deleteHouse(long id)
    {
        repository.deleteById(id);
    }

    public Collection<House> getAllHouses()
    {
        return repository.findAll();
    }
}
