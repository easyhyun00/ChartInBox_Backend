package team2.chartBox.member.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.member.dto.UserEmailResponse;
import team2.chartBox.member.dto.LoginResponse;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;
import team2.chartBox.member.service.MemberService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.time.Duration;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    @Autowired
    private MemberService memberService;
    private MemberRepository memberRepository;

    /*
        로그인
     */
    @PostMapping("/log-in")
    public ResponseEntity loginSubmit(@RequestBody LoginResponse loginResponse, HttpServletRequest request) {

        log.info("받은 거 {}",loginResponse);
        log.info("===========> {} ",loginResponse.getUserEmail());
        log.info("===========> {} ", loginResponse.getUserPassword());

        Member findMember = memberRepository.findByUserEmail(loginResponse.getUserEmail());

        String strMsg = memberService.loginService(findMember, loginResponse);

        if (strMsg != "success") {// userEmail: 없는 이메일, userPassword: 잘못된 비밀번호, auth: 인증X 사용자
            return ResponseEntity.badRequest().body(strMsg);
            // return null;
        }

        log.info("로그인 성공 {}", findMember.getUserEmail());

        // 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER,findMember);

         HttpHeaders headers= new HttpHeaders();
         headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
         // headers.set("mySession",session.getId());
        // headers.setCacheControl("max-age=3600, must-revalidate, no-transform");

        // CacheControl cacheControl = CacheControl.maxAge(Duration.ofDays(1));

//        return ResponseEntity.ok()
//                .cacheControl(cacheControl)
//                .body("success");
        return new ResponseEntity("success",headers, HttpStatus.OK);
    }

    /*
        로그아웃
     */
    @PostMapping("/log-out")
    public ResponseEntity logoutSubmit(HttpServletRequest request) {

        // 세션 삭제
        HttpSession session = request.getSession(false);

        if (session != null) {
            log.info("세션 삭제");
            session.invalidate();
            return ResponseEntity.ok().body(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    /*
        비밀번호 찾기 메일인증
     */
    @PostMapping("/log-in/find-pw")
    public ResponseEntity findPassword(@RequestBody UserEmailResponse userEmailResponse) throws MessagingException, UnsupportedEncodingException {

        String sendEmail = userEmailResponse.getUserEmail();

        log.info("email = {}", sendEmail);

        boolean result = memberService.findPassword(sendEmail);
        if (result == true)
            return ResponseEntity.ok().body(true);
        return ResponseEntity.badRequest().body(false);

    }

}
