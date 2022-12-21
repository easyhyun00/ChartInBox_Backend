package team2.chartBox.member.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.member.dto.JoinResponse;
import team2.chartBox.member.repository.MemberRepository;
import team2.chartBox.member.service.MemberService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class JoinController {

    @Autowired
    private MemberService memberService;
    private MemberRepository memberRepository;

    /*
        회원가입
     */
    @PostMapping("/join")
    public ResponseEntity joinSubmit(@RequestBody JoinResponse joinResponse) throws MessagingException, UnsupportedEncodingException {

        log.info("회원가입 제출");

        log.info(joinResponse.getUserEmail());
        log.info(joinResponse.getUserNickname());
        log.info(joinResponse.getUserPassword());

        String strMsg = memberService.joinService(joinResponse);

        if (strMsg.equals("success")) { // 회원가입 성공
            log.info("회원 가입 성공 {}",joinResponse.getUserEmail());
            memberService.sendAuthMail(joinResponse.getUserEmail()); // 인증 메일 발송
            return ResponseEntity.ok().body(strMsg);
        }
        else{ // 회원가입 실패
            log.info("회원 가입 실패 중복된 {}", strMsg);
            return ResponseEntity.badRequest().body(strMsg);
        }
    }

    /*
        회원가입 메일 인증
     */
    @GetMapping("/join/confirm")
    public ResponseEntity joinConfirm(@RequestParam String userEmail, String authKey) {

        // authKey가 DB에 저장된 authKey와 동일하면, authStatus = 1
        if (memberService.updateAuthStatus(userEmail,authKey)) {
            // 로그인 or 메인 홈으로 리다이렉트?
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

}
