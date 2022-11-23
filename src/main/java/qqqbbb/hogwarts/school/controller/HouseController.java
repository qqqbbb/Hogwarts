package qqqbbb.hogwarts.school.controller;

import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qqqbbb.hogwarts.school.model.House;
import qqqbbb.hogwarts.school.service.HouseService;
import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("house")
public class HouseController
{
    private final HouseService houseService;

    public HouseController(HouseService houseService)
    {
        this.houseService = houseService;
    }

    @PostMapping
    public ResponseEntity addHouse(@RequestBody House house)
    {
        return ResponseEntity.ok(houseService.addHouse(house));
    }

    @GetMapping("{id}")
    public ResponseEntity getHouse(@PathVariable Long id)
    {
//        System.out.println("c getHouse " + id);
        return ResponseEntity.ok(houseService.getHouse(id));
    }

    @PutMapping
    public ResponseEntity editHouse(@RequestBody House house)
    {
        return ResponseEntity.ok(houseService.editHouse(house));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteHouse(@PathVariable Long id)
    {
        houseService.deleteHouse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/color/{color}")
    public ResponseEntity getHousesByColor(@PathVariable String color)
    {
//        System.out.println("c getHousesByColor " + color);
        List<House> houses = houseService.getHousesByColor(color);
        return ResponseEntity.ok(houses);
    }

    @GetMapping("all")
    public ResponseEntity getAllHouses()
    {
        Collection<House> houses = houseService.getAllHouses();
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/colorOrName/{colorOrName}")
    public ResponseEntity getHousesByColorOrName(@PathVariable String colorOrName)
    {
//        System.out.println("c getHousesByColorOrName color " + colorOrName);
        Collection<House> houses = houseService.getHousesByColorOrName(colorOrName);
        return ResponseEntity.ok(houses);
    }

    @GetMapping("/longestName")
    public ResponseEntity getLongestName()
    {
        return ResponseEntity.ok(houseService.getLongestName());
    }
}
