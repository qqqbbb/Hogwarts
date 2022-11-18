package qqqbbb.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("info")
public class InfoController
{
    @Value("${server.port}")
    private int port;

    @GetMapping("/getPort")
    public ResponseEntity getPort()
    {
        return ResponseEntity.ok("Server port: " + port);
    }
}
