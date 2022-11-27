package qqqbbb.hogwarts.school.service;

import org.slf4j.*;
import org.springframework.stereotype.Service;
import qqqbbb.hogwarts.school.Exception.*;
import qqqbbb.hogwarts.school.PrinterThread;
import qqqbbb.hogwarts.school.model.*;
import qqqbbb.hogwarts.school.repository.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService
{
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;
    private final AvatarRepository avatarRepository;

    public StudentService(StudentRepository repository, AvatarRepository avatarRepository)
    {
        this.repository = repository;
        this.avatarRepository = avatarRepository;
    }

    public Student addStudent(Student student)
    {
        logger.info("addStudent " + student.getId());
        return repository.save(student);
    }

    public Student getStudent(long id)
    {
        logger.info("getStudent " + id);
        return repository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student editStudent(Student student)
    {
        logger.info("editStudent " + student.getId());
        getStudent(student.getId());
        return repository.save(student);
    }

    public void deleteStudent(long id)
    {
        logger.info("deleteStudent " + id);
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudents()
    {
        logger.info("getAllStudents ");
        return repository.findAll();
    }

    public List<Student> getStudentsByAge(int age)
    {
        logger.info("getStudentsByAge " + age);
        return repository.findAll().stream().filter(s -> s.getAge() == age).collect(Collectors.toList());
    }

    public Collection<Student> findByAgeBetween(int ageMin, int ageMax)
    {
        logger.info("findByAgeBetween " + ageMin + ' ' + ageMax);
        return repository.findByAgeBetween(ageMin, ageMax);
    }

    public Student patchStudentAvatar(long studentId, long avatarId)
    {
        logger.info("patchStudentAvatar " + studentId + ' ' + avatarId);
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
        logger.info("countStudents ");
        return repository.countStudents();
    }

    public int getStudentsAverageAgeSQL()
    {
        logger.info("getStudentsAverageAgeSQL ");
        return repository.getAverageAge();
    }

    public double getStudentsAverageAge()
    {
        logger.info("getStudentsAverageAge ");
        return repository.findAll().stream().mapToInt(Student::getAge).average().orElseThrow(StudentNotFoundException::new);
    }

    public  Collection<Student> getLast5Students()
    {
        logger.info("getLast5Students ");
        return repository.getLast5Students();
    }

    public List<String> getNamesStartWith(String name)
    {
        logger.info("getNamesStartWith " + name);
        return repository.findAll().stream().map(Student::getName).filter(n -> n.startsWith(name)).collect(Collectors.toList());
    }

    public void printNames(Boolean sync)
    {
        logger.info("printNames sync " + sync);
        List<String> names = repository.findAll().stream().map(Student::getName).toList();
        PrinterThread thread1 = new PrinterThread();
        PrinterThread thread2 = new PrinterThread();
        thread1.setStrings(names.subList(2, 4));
        thread2.setStrings(names.subList(4, 6));
        if (sync)
        {
            thread1.setSyncObj(this);
            thread2.setSyncObj(this);
            thread1.setPriority(10);
        }
        for (int i = 0; i <= 1; i++)
        {
            String name = names.get(i);
            if (name == null || name.isEmpty())
                continue;

            System.out.println("main thread " + name );
        }
        thread1.start();
        thread2.start();

        for (int i = 6; i < names.size(); i++)
        {
            String name = names.get(i);
            if (name == null || name.isEmpty())
                continue;

            System.out.println("main thread " + name );
        }
    }
}
