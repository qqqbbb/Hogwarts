package qqqbbb.hogwarts.school.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import qqqbbb.hogwarts.school.model.Student;
import qqqbbb.hogwarts.school.repository.StudentRepository;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService
{
    private final StudentRepository repository;

    public StudentService(StudentRepository repository)
    {
        this.repository = repository;
    }

    public Student addStudent(Student student)
    {
        return repository.save(student);
    }

    public Student getStudent(long id)
    {
        return repository.findById(id).orElseThrow();
    }

    public Student editStudent(Student student)
    {
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
}
