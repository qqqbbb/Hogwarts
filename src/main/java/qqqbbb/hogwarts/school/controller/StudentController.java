package qqqbbb.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qqqbbb.hogwarts.school.model.*;
import qqqbbb.hogwarts.school.service.StudentService;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("student")
@RestController
public class StudentController
{
    private final StudentService studentService;

    public StudentController(StudentService studentService)
    {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity addStudent(@RequestBody Student student)
    {
        return ResponseEntity.ok(studentService.addStudent(student));
    }

    @GetMapping("{id}")
    public ResponseEntity getStudent(@PathVariable long id)
    {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PutMapping
    public ResponseEntity editStudent(@RequestBody Student student)
    {
        return ResponseEntity.ok( studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable long id)
    {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age/{age}")
    public ResponseEntity getStudentsByAge(@PathVariable int age)
    {
        List<Student> students = studentService.getStudentsByAge(age);
        return ResponseEntity.ok(students);
    }

    @GetMapping("all")
    public ResponseEntity getAllStudents()
    {
        Collection<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
