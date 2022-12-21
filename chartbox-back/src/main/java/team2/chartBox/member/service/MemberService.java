package team2.chartBox.member.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team2.chartBox.member.dto.JoinResponse;
import team2.chartBox.member.dto.LoginResponse;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.mail.MailUtils;
import team2.chartBox.member.repository.MemberRepository;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JavaMailSender mailSender;


    /*
        회원가입
     */
    public String joinService(JoinResponse joinResponse) {

        if(memberRepository.findByUserEmail(joinResponse.getUserEmail()) != null) {
            return "userEmail"; // 존재하는 이메일 = 회원 가입 불가능
        }
        else if (memberRepository.findByUserNickname(joinResponse.getUserNickname()) != null) {
            return "userNickname"; // 존재하는 닉네임 = 회원 가입 불가능
        }

        log.info(joinResponse.getUserEmail());
        log.info(joinResponse.getUserNickname());
        log.info(joinResponse.getUserPassword());

        // 비밀번호 암호화
        String encPassword = bCryptPasswordEncoder.encode(joinResponse.getUserPassword());
        joinResponse.setUserPassword(encPassword);

        log.info("encPassword = {}", encPassword);

        Member saveMember = new Member(joinResponse);

        // 인증 번호 난수 생성
        String authKey = getAuthCode(6);
        saveMember.setAuthKey(authKey);

        // DB 저장
        memberRepository.save(saveMember);
        return "success";
    }

    /*
        로그인
     */
    public String loginService(Member findMember,LoginResponse loginResponse) {

        if (findMember == null)
            return "userEmail"; // 없는 이메일 = 로그인 불가능

        if (findMember.getAuthStatus().equals(0))
            return "auth"; // 인증되지 않은 사용자 = 로그인 불가능

        if(bCryptPasswordEncoder.matches(loginResponse.getUserPassword(),findMember.getUserPassword()))
            return "success"; // 비밀 번호 일치 = 로그인 성공

        return "userPassword"; // 일치 하지 않는 비밀번호 = 로그인 불가능
    }

    /*
        임시 비밀번호 생성
    */
    public String getTmpPassword() {
        char[] charSet = new char[]{ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String password = "";

        /* 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 조합 */
        int idx = 0;
        for(int i = 0; i < 10; i++){
            idx = (int) (charSet.length * Math.random());
            password += charSet[idx];
        }

        log.info("생성된 임시 비밀번호 = {}" ,password);

        return password;
    }

    /*
        비밀번호 업데이트
    */
    public boolean updatePassword(String tmpPassword, String userEmail) {

        // memberEmail 회원 존재하는지 찾음
        Member member = memberRepository.findByUserEmail(userEmail);

        if (member == null)
            return false;

        // 존재하면 임시 비밀번호로 업데이트, 암호화 하여 DB에 저장
        member.setUserPassword(bCryptPasswordEncoder.encode(tmpPassword));
        memberRepository.save(member);

        return true;
    }

    /*
        비밀번호 찾기 메일 전송
     */
    public void mailSend(String tmpPassword, String userEmail) throws MessagingException, UnsupportedEncodingException {

        MailUtils sendMail = new MailUtils(mailSender);

        sendMail.setSubject("Chart In Box 임시 비밀번호 안내 메일 입니다.");
        sendMail.setText(new StringBuffer()
                .append("<h1>[Chart In Box]</h1><br/>")
                .append("<h2>임시 비밀번호</h2><br/>")
                .append("<p>회원님의 임시 비밀번호는 ")
                .append(tmpPassword)
                .append(" 입니다.</p><br/>")
                .append("로그인 후 비밀번호를 변경해 주세요.")
                .toString());
        sendMail.setFrom("chartinbox1234@gmail.com","차트인박스");
        sendMail.setTo(userEmail);
        sendMail.send();

        log.info("메일 전송 완료");
    }

    /*
        비밀번호 찾기
     */
    public boolean findPassword(String userEmail) throws MessagingException, UnsupportedEncodingException {

        if (memberRepository.findByUserEmail(userEmail) == null)
            return false; // DB에 존재하지 않는 이메일

        // 임시 비밀번호 생성
        String tmpPw = getTmpPassword();
        // 임시 비밀번호 DB에 저장
        updatePassword(tmpPw,userEmail);
        // 메일 전송
        mailSend(tmpPw,userEmail);

        return true;

    }

    /*
        인증코드 난수 발생
    */
    private String getAuthCode(int size) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while (buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }

    /*
        회원가입 인증메일 보내기
     */
    public void sendAuthMail(String userEmail) throws MessagingException, UnsupportedEncodingException {

        Member member = memberRepository.findByUserEmail(userEmail);

        log.info("메일 보내기");

        // 인증메일 보내기
        MailUtils sendMail = new MailUtils(mailSender);
        sendMail.setSubject("Chart In Box 회원가입 이메일 인증 메일 입니다.");
        sendMail.setText(new StringBuffer()
                .append("<h1>[Chart In Box]</h1><br/>")
                .append("<h2>이메일 인증</h2><br/>")
                .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p><br/>")
                .append("<a href='http://localhost:8080/join/confirm?userEmail=")
                .append(userEmail)
                .append("&authKey=")
                .append(member.getAuthKey())
                .append("' target='_blenk'>이메일 인증 확인</a>")
                .toString());
        sendMail.setFrom("chartinbox1234@gmail.com","차트인박스");
        sendMail.setTo(userEmail);
        sendMail.send();

        log.info("메일 다 보냄");
    }

    /*
        authKey 일치 확인
     */
    public boolean updateAuthStatus(String userEmail, String authKey) {

        Member member = memberRepository.findByUserEmail(userEmail);
        if (member.getAuthKey().equals(authKey)) {
            member.setAuthStatus(1);
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
