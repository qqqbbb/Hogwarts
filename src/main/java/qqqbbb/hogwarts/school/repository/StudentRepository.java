package qqqbbb.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qqqbbb.hogwarts.school.model.Student;
import java.util.Collection;

public interface StudentRepository extends JpaRepository <Student, Long>
{
    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    @Query(value = "SELECT COUNT(*) as studentCount FROM students; ", nativeQuery = true)
    int countStudents();

    @Query(value = "SELECT AVG(age) as averageAge FROM students; ", nativeQuery = true)
    int getAverageAge();

    @Query(value = "SELECT * FROM students order by id desc limit 5; ", nativeQuery = true)
    Collection<Student> getLast5Students();
}