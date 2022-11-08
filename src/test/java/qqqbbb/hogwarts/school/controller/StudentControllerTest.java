package qqqbbb.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import qqqbbb.hogwarts.school.model.*;
import qqqbbb.hogwarts.school.repository.StudentRepository;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
class StudentControllerTest
{

    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private static StudentRepository studentRepository;
    static Student student1;
    static long student1id = 1;
    static String student1name = "student1name";
    private static final int student1age = 11;
    static Student student2;
    static long student2id = 2;
    static String student2name = "student2name";
    private static final int student2age = 22;
    static Student student3;
    static long student3id = 1;
    static String student3name = "student3name";
    private static final int student3age = 33;

//    static House house1;
//    static long house1id = 1;
//    static String house1name = "house1name";
//    static String house1color = "house1color";
//    static House house2;
//    static long house2id = 1;
//    static String house2name = "house2name";
//    static String house2color = "house2color";
//    static House house3;
    static JSONObject student1JSONObject;

    static void createStudent(Student student)
    {
//        studentRepository.saveAndFlush(student);
//        System.out.println("create Student " + student.getName());
//        System.out.println("studentRepository " + studentRepository.count());
    }

    @BeforeAll
    static void setUp()
    {
//        studentRepository.deleteAll();
//        house1 = new House();
//        house1.setId(house1id);
//        house1.setName(house1name);
//        house1.setColor(house1color);
//        house2 = new House();
//        house2.setId(2);
//        house2.setName(house2name);
//        house2.setColor(house2color);

        student1 = new Student();
        student1.setId(student1id);
        student1.setName(student1name);
        student1.setAge(student1age);
//        student1.setHouse(house1);
        student2 = new Student();
        student2.setId(student2id);
        student2.setName(student2name);
        student2.setAge(student2age);
//        student2.setHouse(house1);
        student3 = new Student();
        student3.setId(student2id);
        student3.setName(student3name);
        student3.setAge(student3age);
//        student3.setHouse(house2);

        student1JSONObject = new JSONObject();
        student1JSONObject.put("id", student1id);
        student1JSONObject.put("name", student1name);
        student1JSONObject.put("age", student1age);

    }

    @AfterAll
    public static void resetDb()
    {
        if (studentRepository == null)
        {
            System.out.println("resetDb studentRepository null !");
            return;
        }
        studentRepository.deleteAll();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testAddStudent() throws Exception
    {
        assertNotNull(student1);
        String url = "http://localhost:" + port + "/student";
//        String postString = restTemplate.postForObject("http://localhost:" + port + "/student", student1, String.class);
        ResponseEntity<Student> response = restTemplate.postForEntity(url, student1, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), student1id);
        assertEquals(response.getBody().getName(), student1name);
//        restTemplate.postForEntity(url, student2, Student.class);
    }

    @Test
//    @Order(2)
    void testGetStudent()
    {
        assertNotNull(student1);
        String url = "http://localhost:" + port + "/student/" + student1id;
        ResponseEntity<Student> response = restTemplate.getForEntity(url, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), student1id);
        assertEquals(response.getBody().getName(), student1name);
    }

    @Test
//    @Order(3)
    void testEditStudent()
    {
        assertNotNull(student1);
        HttpEntity<Student> entity = new HttpEntity<Student>(student1);
        String url = "http://localhost:" + port + "/student";
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getId(), student1id);
        assertEquals(response.getBody().getName(), student1name);
    }

//    @Test
    void testDeleteStudent()
    {
//        ResponseEntity<Student> response =  restTemplate.delete("http://localhost:" + port + "/student/1", String.class);
    }

    @Test
    void testGetStudentsByAge()
    {
        assertNotNull(student1);
        assertNotNull(student2);
        String postStudentUrl = "http://localhost:" + port + "/student";
        restTemplate.postForEntity(postStudentUrl, student1, Student.class);
        restTemplate.postForEntity(postStudentUrl, student2, Student.class);
        String url = "http://localhost:" + port + "/student/age/" + student2age;
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        List<Student> students = response.getBody();
        System.out.println("testGetStudentsByAge students list size " + students.size());
        System.out.println("testGetStudentsByAge students list getName " + students.get(0).getName());
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getName(), student2name);
    }

    @Test
    public void testGetAllStudents() throws Exception
    {
        assertNotNull(student1);
        assertNotNull(student2);
        String postStudentUrl = "http://localhost:" + port + "/student";
        restTemplate.postForEntity(postStudentUrl, student2, Student.class);
        String url = "http://localhost:" + port + "/student/all";
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        List<Student> students = response.getBody();
        System.out.println("testGetAllStudents students list size " + students.size());
        System.out.println("testGetAllStudents students list getName " + students.get(0).getName());
        assertEquals(students.size(), 2);
        assertEquals(students.get(0).getName(), student1name);
    }

    @Test
    void testGetStudentsByAgeBetween()
    {
        assertNotNull(student1);
        assertNotNull(student2);
        String postStudentUrl = "http://localhost:" + port + "/student";
        restTemplate.postForEntity(postStudentUrl, student1, Student.class);
        restTemplate.postForEntity(postStudentUrl, student2, Student.class);
        String url = "http://localhost:" + port + "/student/ageBetween/"  + 10 + '&' + 20;
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        List<Student> students = response.getBody();
        System.out.println("testGetStudentsByAgeBetween students list size " + students.size());
        System.out.println("testGetStudentsByAgeBetween students list getName " + students.get(0).getName());
        assertEquals(students.size(), 1);
        assertEquals(students.get(0).getName(), student1name);
    }
}