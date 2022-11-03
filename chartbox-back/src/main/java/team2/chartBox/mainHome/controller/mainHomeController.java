package team2.chartBox.mainHome.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import team2.chartBox.SessionConst;
import team2.chartBox.member.dto.LoginResponse;
import team2.chartBox.member.entity.Member;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class mainHomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member member) {
        if (member == null) {
            return null;
        }
        return member.getUserNickname();

    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }
}
