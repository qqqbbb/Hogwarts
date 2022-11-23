package qqqbbb.hogwarts.school.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import qqqbbb.hogwarts.school.Exception.*;
import qqqbbb.hogwarts.school.model.House;
import qqqbbb.hogwarts.school.repository.HouseRepository;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HouseService
{
    Logger logger = LoggerFactory.getLogger(HouseService.class);
    private final HouseRepository repository;

    public HouseService(HouseRepository repository)
    {
        this.repository = repository;
    }

    public House addHouse(House house)
    {
        logger.info("addHouse " + house.getId());
        return repository.save(house);
    }

    public House getHouse(long id)
    {
        logger.info("getHouse " + id);
        return repository.findById(id).orElseThrow(HouseNotFoundException::new);
    }

    public House editHouse(House house)
    {
        logger.info("editHouse " + house.getId());
        getHouse(house.getId());
        return repository.save(house);
    }

    public void deleteHouse(long id)
    {
        logger.info("deleteHouse " + id);
        repository.deleteById(id);
    }

    public Collection<House> getAllHouses()
    {
        logger.info("getAllHouses ");
        return repository.findAll();
    }

    public  List<House> getHousesByColor(String color)
    {
        logger.info("getHousesByColor " + color);
        return repository.findAll().stream().filter(h -> h.getColor().equals(color)).collect(Collectors.toList());
    }

    public  Collection<House> getHousesByColorOrName(String colorOrName)
    {
        logger.info("getHousesByColorOrName " + colorOrName);
        Collection<House> houses = repository.findByColorIgnoreCase(colorOrName);
        if (houses.size() > 0)
            return houses;

        return repository.findByNameIgnoreCase(colorOrName);
    }

    public String getLongestName()
    {
        logger.info("getLongestName ");
        Optional optional = repository.findAll().stream().map(House::getName).max(Comparator.comparingInt(String::length));
        if (optional.isEmpty())
            throw new HouseNotFoundException();

        return (String)optional.get();
    }
}
