package qqqbbb.hogwarts.school.service;

import org.springframework.stereotype.Service;
import qqqbbb.hogwarts.school.model.Student;
import qqqbbb.hogwarts.school.repository.StudentRepository;
import java.util.*;

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
        return repository.findById(id).get();
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
}
