package qqqbbb.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import qqqbbb.hogwarts.school.model.Avatar;
import qqqbbb.hogwarts.school.model.Student;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long>
{
    Optional<Avatar> findByStudentId(Long studentId);

    void deleteByStudent_Id(Long studentId);

}
