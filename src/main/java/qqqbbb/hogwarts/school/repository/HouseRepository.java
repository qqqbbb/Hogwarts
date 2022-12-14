package qqqbbb.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qqqbbb.hogwarts.school.model.House;
import java.util.Collection;


public interface HouseRepository extends JpaRepository<House, Long>
{

    Collection<House> findByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

    Collection<House> findByColorIgnoreCase(String color);

    Collection<House> findByNameIgnoreCase(String name);


}
