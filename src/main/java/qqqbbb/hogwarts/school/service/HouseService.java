package qqqbbb.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import qqqbbb.hogwarts.school.Exception.*;
import qqqbbb.hogwarts.school.model.House;
import qqqbbb.hogwarts.school.repository.HouseRepository;
import java.util.*;
import java.util.stream.Collectors;

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
        return repository.findById(id).orElseThrow(HouseNotFoundException::new);
    }

    public House editHouse(House house)
    {
        getHouse(house.getId());
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

    public  List<House> getHousesByColor(String color)
    {
        return repository.findAll().stream().filter(h -> h.getColor().equals(color)).collect(Collectors.toList());
    }

    public  Collection<House> getHousesByColorOrName(String colorOrName)
    {
        Collection<House> houses = repository.findByColorIgnoreCase(colorOrName);
        if (houses.size() > 0)
            return houses;

        return repository.findByNameIgnoreCase(colorOrName);
    }
}
