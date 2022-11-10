package qqqbbb.hogwarts.school.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import qqqbbb.hogwarts.school.model.Avatar;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long>
{
    Optional<Avatar> findByStudentId(Long studentId);

    void deleteByStudent_Id(Long studentId);

    Page<Avatar> findAll(Pageable pageable);
}
