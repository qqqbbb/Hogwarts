package qqqbbb.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qqqbbb.hogwarts.school.model.Student;
import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long>
{
    Collection<Student> findByAgeBetween(int ageMin, int ageMax);
}