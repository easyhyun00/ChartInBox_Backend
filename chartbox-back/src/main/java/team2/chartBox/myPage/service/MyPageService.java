package team2.chartBox.myPage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;
import team2.chartBox.myPage.dto.EditNicknameResponse;
import team2.chartBox.myPage.dto.EditPasswordResponse;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private FreeBoardRepository freeBoardRepository;

    /*
        닉네임 변경
     */
    public String editNickname(EditNicknameResponse editNicknameRes) {
        if (memberRepository.findByUserNickname(editNicknameRes.getNewNickname()) != null) {
            // 이미 사용중인 닉네임
            return "fail";
        }

        // 변경할 member 찾아, 닉네임 변경 후 DB에 저장
        Member findMember = memberRepository.findByUserEmail(editNicknameRes.getUserEmail());
        findMember.setUserNickname(editNicknameRes.getNewNickname());
        memberRepository.save(findMember);
        return "success";
    }

    /*
        비밀번호 변경
     */
    public String editPassword(EditPasswordResponse editPasswordRes) {

        Member findMember = memberRepository.findByUserEmail(editPasswordRes.getUserEmail());

        if (bCryptPasswordEncoder.matches(editPasswordRes.getUserPassword(), findMember.getUserPassword())) {
            log.info("변경될 비밀번호 {}",editPasswordRes.getNewPassword());
            String encPassword = bCryptPasswordEncoder.encode(editPasswordRes.getNewPassword()); // 비밀번호 암호화
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

    /*
        회원이 작성한 글 목록
     */
    public List<FreeBoard> getFreeBoardList(String userNickname) {
        return freeBoardRepository.findAllByPostUserNickname(userNickname);
    }
}
