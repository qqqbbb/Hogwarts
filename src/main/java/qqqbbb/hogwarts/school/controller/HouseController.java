package qqqbbb.hogwarts.school.controller;

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
    public ResponseEntity addHouse(@RequestBody House houseToAdd)
    {
        House house = houseService.addHouse(houseToAdd);
        if (house == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(house);
    }

    @GetMapping("{id}")
    public ResponseEntity getHouse(@PathVariable Long id)
    {
        House house = houseService.getHouse(id);
        if (house == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(house);
    }

    @PutMapping
    public ResponseEntity editHouse(@RequestBody House houseToEdit)
    {
        House house = houseService.editHouse(houseToEdit);
        if (house == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(house);
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
        List<House> houses = houseService.getAllHouses().stream().filter(h -> h.getColor().equals(color)).collect(Collectors.toList());
        return ResponseEntity.ok(houses);
    }

    @GetMapping("all")
    public ResponseEntity getAllHouses()
    {
        Collection<House> houses = houseService.getAllHouses();
        return ResponseEntity.ok(houses);
    }

}
