package qqqbbb.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import qqqbbb.hogwarts.school.model.*;
import qqqbbb.hogwarts.school.repository.StudentRepository;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @MockBean
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

    static List<Student> students = new ArrayList<>();
    static JSONObject student1JSONObject;

    @BeforeAll
    static void setUp()
    {
        student1 = new Student();
        student1.setId(student1id);
        student1.setName(student1name);
        student1.setAge(student1age);
        student2 = new Student();
        student2.setId(student2id);
        student2.setName(student2name);
        student2.setAge(student2age);
        student3 = new Student();
        student3.setId(student3id);
        student3.setName(student3name);
        student3.setAge(student3age);

        students.add(student1);
        students.add(student2);
        students.add(student3);

        student1JSONObject = new JSONObject();
        student1JSONObject.put("id", student1id);
        student1JSONObject.put("name", student1name);
        student1JSONObject.put("age", student1age);
    }

//    @AfterAll
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
    public void testRepo()
    {
        assertNotNull(studentRepository);
    }

    @Test
    public void testAddStudent() throws Exception
    {
        String url = "http://localhost:" + port + "/student";
        when(studentRepository.save(any(Student.class))).thenReturn(student1);
//        String postString = restTemplate.postForObject("http://localhost:" + port + "/student", student1, String.class);
        ResponseEntity<Student> response = restTemplate.postForEntity(url, student1, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), student1);
    }

    @Test
    void testGetStudent()
    {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student1));
        String url = "http://localhost:" + port + "/student/" + student1id;
        ResponseEntity<Student> response = restTemplate.getForEntity(url, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), student1);
    }

    @Test
    void testEditStudent()
    {
        when(studentRepository.save(any(Student.class))).thenReturn(student1);
        HttpEntity<Student> entity = new HttpEntity<Student>(student1);
        String url = "http://localhost:" + port + "/student";
        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Student.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), student1);
    }

    @Test
    void testDeleteStudent()
    {
//        restTemplate.postForEntity(url, student1, Student.class);
        String url = "http://localhost:" + port + "/student/" + student1id;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        System.out.println("testDeleteStudent response Status " + response.getStatusCode());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetStudentsByAge()
    {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(student1)));
        String url = "http://localhost:" + port + "/student/age/" + student1age;
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        List<Student> returnedStudents = response.getBody();
        System.out.println("testGetStudentsByAge students list size " + returnedStudents.size());
        assertEquals(returnedStudents.size(), 1);
        assertEquals(returnedStudents.get(0), student1);
    }

    @Test
    public void testGetAllStudents() throws Exception
    {
        when(studentRepository.findAll()).thenReturn(students);
        String postStudentUrl = "http://localhost:" + port + "/student";
        restTemplate.postForEntity(postStudentUrl, student2, Student.class);
        String url = "http://localhost:" + port + "/student/all";
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        List<Student> returnedStudents = response.getBody();
        System.out.println("testGetAllStudents students list size " + returnedStudents.size());
        assertEquals(returnedStudents.size(), students.size());
        assertTrue(returnedStudents.contains(student1));
        assertTrue(returnedStudents.contains(student2));
        assertTrue(returnedStudents.contains(student3));
    }

    @Test
    void testGetStudentsByAgeBetween()
    {
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(new ArrayList<>(Arrays.asList(student1)));
        String url = "http://localhost:" + port + "/student/ageBetween/"  + 10 + '&' + 20;
        ResponseEntity<List<Student>> response = restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {});
        List<Student> students = response.getBody();
        System.out.println("testGetStudentsByAgeBetween students list size " + students.size());
        assertEquals(students.size(), 1);
        assertEquals(students.get(0), student1);
    }
}