package team2.chartBox.myPage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;

@Service
@AllArgsConstructor
@Slf4j
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
        닉네임 변경
     */
    public String editNickname(String userEmail, String newNickname) {
        if (memberRepository.findByUserNickname(newNickname) != null) {
            // 이미 사용중인 닉네임
            return "fail";
        }

        // 변경할 member 찾아, 닉네임 변경 후 DB에 저장
        Member findMember = memberRepository.findByUserEmail(userEmail);
        findMember.setUserNickname(newNickname);
        memberRepository.save(findMember);
        return "success";
    }

    /*
        비밀번호 변경
     */
    public String editPassword(String userEmail, String userPassword, String newPassword) {

        Member findMember = memberRepository.findByUserEmail(userEmail);

        if (bCryptPasswordEncoder.matches(userPassword, findMember.getUserPassword())) {
            log.info("변경될 비밀번호 {}",newPassword);
            String encPassword = bCryptPasswordEncoder.encode(newPassword); // 비밀번호 암호화
            findMember.setUserPassword(encPassword);
            memberRepository.save(findMember);
            return "success";
        }
        // 잘못 입력된 비밀번호
        return "fail";
    }

    /*
        회원 탈퇴
     */
    public String withdrawMember(Member member) {
        Member findMember = memberRepository.findByUserEmail(member.getUserEmail());
        if (findMember == null)
            return "fail";
        memberRepository.delete(findMember);
        return "success";
    }
}
