package qqqbbb.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qqqbbb.hogwarts.school.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>
{

}