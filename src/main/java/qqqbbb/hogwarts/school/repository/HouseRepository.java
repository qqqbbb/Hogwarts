package qqqbbb.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qqqbbb.hogwarts.school.model.House;

public interface HouseRepository extends JpaRepository<House, Long>
{

}
