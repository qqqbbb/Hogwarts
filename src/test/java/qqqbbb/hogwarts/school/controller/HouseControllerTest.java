package qqqbbb.hogwarts.school.controller;

import net.minidev.json.JSONObject;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import qqqbbb.hogwarts.school.model.House;
import qqqbbb.hogwarts.school.repository.*;
import qqqbbb.hogwarts.school.service.*;
import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HouseController.class)
class HouseControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HouseRepository houseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private HouseService houseService;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private StudentService studentService;

    @InjectMocks
    private HouseController houseController;

    static JSONObject house1JSONObject;
    static JSONObject house2JSONObject;
    static House house1;
    static long house1id = 1L;
    static String house1name = "House1name";
    static String house1color = "House1color";

    static House house2;
    static long house2id = 2L;
    static String house2name = "House2name";
    static String house2color = "House2color";
    static List<House> houses = new ArrayList<>();

    @BeforeAll
    static void setUp()
    {
        house1JSONObject = new JSONObject();
        house1JSONObject.put("id", house1id);
        house1JSONObject.put("name", house1name);
        house1JSONObject.put("color", house1color);
        house1 = new House();
        house1.setId(house1id);
        house1.setName(house1name);
        house1.setColor(house1color);

        house2JSONObject = new JSONObject();
        house2JSONObject.put("id", house2id);
        house2JSONObject.put("name", house2name);
        house2JSONObject.put("color", house2color);
        house2 = new House();
        house2.setId(house2id);
        house2.setName(house2name);
        house2.setColor(house2color);

        houses.add(house1);
        houses.add(house2);
    }

    @Test
    public void testAddHouse() throws Exception
    {
        when(houseRepository.save(any(House.class))).thenReturn(house1);
//        when(houseRepository.findById(any(Long.class))).thenReturn(Optional.of(house1));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/house") //send
                .content(house1JSONObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(house1id))
                .andExpect(jsonPath("$.name").value(house1name))
                .andExpect(jsonPath("$.color").value(house1color));
    }

    @Test
    void testGetHouse() throws Exception
    {
        when(houseRepository.findById(house1id)).thenReturn(Optional.of(house1));
        String url = "/house/" + house1id;
        mockMvc.perform(MockMvcRequestBuilders
                .get(url) //send
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(house1id))
                .andExpect(jsonPath("$.name").value(house1name))
                .andExpect(jsonPath("$.color").value(house1color));
    }

    @Test
    void testEditHouse() throws Exception
    {
        when(houseRepository.save(any(House.class))).thenReturn(house1);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/house") //send
                .content(house1JSONObject.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(house1id))
                .andExpect(jsonPath("$.name").value(house1name))
                .andExpect(jsonPath("$.color").value(house1color));
    }

    @Test
    void testDeleteHouse() throws Exception
    {
        String url = "/house/" + house1id;
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(url))//send
                        .andExpect(status().isOk()); //receive
    }

    @Test
    void testGetHousesByColor() throws Exception
    {
        when(houseRepository.findAll()).thenReturn(houses);
        String urlString = "/house/color/" + house1color;
        mockMvc.perform(MockMvcRequestBuilders
                .get(urlString) //send
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.[0].id").value(house1id))
                .andExpect(jsonPath("$.[0].name").value(house1name))
                .andExpect(jsonPath("$.[0].color").value(house1color));
    }

    @Test
    void testGetAllHouses() throws Exception
    {
        when(houseRepository.findAll()).thenReturn(houses);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/house/all") //send
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.[0].id").value(house1id))
                .andExpect(jsonPath("$.[0].name").value(house1name))
                .andExpect(jsonPath("$.[0].color").value(house1color))
                .andExpect(jsonPath("$.[1].id").value(house2id))
                .andExpect(jsonPath("$.[1].name").value(house2name))
                .andExpect(jsonPath("$.[1].color").value(house2color));
    }

    @Test
    void testGetHousesByColorOrName() throws Exception
    {
        when(houseRepository.findByColorIgnoreCaseOrNameIgnoreCase(house1color, null)).thenReturn(Arrays.asList(house1));
        String url = "/house/colorOrName/" + house1color;
        System.out.println("testGetHousesByColorOrName url " + url);
        mockMvc.perform(MockMvcRequestBuilders
                .get(url) //send
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.[0].id").value(house1id))
                .andExpect(jsonPath("$.[0].name").value(house1name))
                .andExpect(jsonPath("$.[0].color").value(house1color));
    }
}