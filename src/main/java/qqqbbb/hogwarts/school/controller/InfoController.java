package qqqbbb.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Stream;

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

    @GetMapping("/sum")
    public ResponseEntity sum ()
    {
        long startTime = System.nanoTime();
        long l = Stream.iterate(1, a -> a +1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b );
        long stopTime = System.nanoTime();
        System.out.println("sum " + (stopTime - startTime));
        return ResponseEntity.ok(l);
    }

}
