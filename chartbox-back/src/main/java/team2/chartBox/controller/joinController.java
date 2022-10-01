package team2.chartBox.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class joinController {
    @GetMapping("/join")
    public String springDataTest(){
        return "스프링에서 보내는 데이터";
    }
}
