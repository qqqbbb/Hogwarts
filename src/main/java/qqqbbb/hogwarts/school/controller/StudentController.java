package qqqbbb.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qqqbbb.hogwarts.school.model.*;
import qqqbbb.hogwarts.school.service.StudentService;
import java.util.*;

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
        Student returnedStudent = studentService.addStudent(student);
        System.out.println("returnedStudent " + returnedStudent);
        return ResponseEntity.ok(returnedStudent);
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

    @GetMapping("/ageBetween/{ageMin}&{ageMax}")
    public ResponseEntity getStudentsByAgeBetween(@PathVariable int ageMin, @PathVariable int ageMax)
    {
        Collection<Student> students = studentService.findByAgeBetween(ageMin, ageMax);
        return ResponseEntity.ok(students);
    }

    @PatchMapping ("/avatar/{studentId}&{avatarId}")
    public ResponseEntity patchStudentAvatar(@PathVariable long studentId, @PathVariable long avatarId)
    {
        return ResponseEntity.ok(studentService.patchStudentAvatar(studentId, avatarId));
    }

    @GetMapping("/count")
    public ResponseEntity countStudents()
    {
        return ResponseEntity.ok(studentService.countStudents());
    }

    @GetMapping("/averageAgeSQL")
    public ResponseEntity getStudentsAverageAgeSQL()
    {
        return ResponseEntity.ok(studentService.getStudentsAverageAgeSQL());
    }

    @GetMapping("/averageAge")
    public ResponseEntity getStudentsAverageAge()
    {
        return ResponseEntity.ok(studentService.getStudentsAverageAge());
    }

    @GetMapping("/last5")
    public ResponseEntity getLast5Students()
    {
        return ResponseEntity.ok(studentService.getLast5Students());
    }

    @GetMapping("/nameStartWith/{name}")
    public ResponseEntity getNamesStartWith(@PathVariable String name)
    {
        return ResponseEntity.ok(studentService.getNamesStartWith(name));
    }

    @GetMapping("/printNames")
    public void printNames()
    {
        studentService.printNames(false);
    }

    @GetMapping("/printNamesSync")
    public void printNamesSync()
    {
        studentService.printNames(true);
    }

}
