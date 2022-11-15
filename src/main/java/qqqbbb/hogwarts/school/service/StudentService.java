package qqqbbb.hogwarts.school.service;

import org.springframework.stereotype.Service;
import qqqbbb.hogwarts.school.Exception.*;
import qqqbbb.hogwarts.school.model.Avatar;
import qqqbbb.hogwarts.school.model.Student;
import qqqbbb.hogwarts.school.repository.AvatarRepository;
import qqqbbb.hogwarts.school.repository.StudentRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService
{
    private final StudentRepository repository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentRepository repository, AvatarRepository avatarRepository)
    {
        this.repository = repository;
        this.avatarRepository = avatarRepository;
    }

    public Student addStudent(Student student)
    {
        return repository.save(student);
    }

    public Student getStudent(long id)
    {
        return repository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student editStudent(Student student)
    {
        getStudent(student.getId());
        return repository.save(student);
    }

    public void deleteStudent(long id)
    {
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudents()
    {
        return repository.findAll();
    }

    public List<Student> getStudentsByAge(int age)
    {
        return repository.findAll().stream().filter(s -> s.getAge() == age).collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax)
    {
        return repository.findByAgeBetween(ageMin, ageMax);
    }

    public Student patchStudentAvatar(long studentId, long avatarId)
    {
        Optional<Student> optionalStudent = repository.findById(studentId);
        Optional<Avatar> optionalAvatar = avatarRepository.findById(avatarId);
        if (optionalStudent.isEmpty())
            throw new StudentNotFoundException();

        if (optionalAvatar.isEmpty())
            throw new AvatarNotFoundException();

        Student student = optionalStudent.get();
        Avatar avatar = optionalAvatar.get();
        student.setAvatarsDir(avatar.getFilePath());
        return repository.save(student);
    }

    public int countStudents()
    {
        return repository.countStudents();
    }

    public int getStudentsAverageAge()
    {
        return repository.getAverageAge();
    }

    public  Collection<Student> getLast5Students()
    {
        return repository.getLast5Students();
    }
}
