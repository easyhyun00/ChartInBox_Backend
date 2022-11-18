package team2.chartBox.myPage.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.member.entity.Member;
import team2.chartBox.myPage.dto.EditMyPageResponse;
import team2.chartBox.myPage.dto.EditMyPageResult;
import team2.chartBox.myPage.dto.MyPageResponse;
import team2.chartBox.myPage.service.MyPageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class MyPageController {

    @Autowired
    private MyPageService myPageService;

    /*
        마이페이지 화면
        우선 닉네임, 이메일
        추후에 작성한 게시물, 댓글, 스크랩한 글, 스크랩한 영화
     */
    @GetMapping("/myPage")
    public ResponseEntity<MyPageResponse> myPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        log.info("이메일 = {}" , member.getUserEmail());
        log.info("닉네임 = {}", member.getUserNickname());

        MyPageResponse myPageResponse = new MyPageResponse(member.getUserEmail(), member.getUserNickname());

        return ResponseEntity.ok().body(myPageResponse);
    }

    /*
        정보 수정 화면
        세션을 통해 닉네임 전송
     */
    @GetMapping("/myPage/edit")
    public ResponseEntity myPageEdit(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        log.info("이메일 = {}" , member.getUserEmail());

        return ResponseEntity.ok().body(member.getUserEmail());
    }

    /*
        정보 수정 제출
        닉네임 변경, 비밀번호 변경

        [닉네임 변경]
        변경할 닉네임이 DB에 있는지, 있으면 이미 있는 닉네임입니다.
        없으면 새 닉네임 DB 업데이트

        [비밀번호 변경]
        현재 비밀번호 맞는지 확인, 틀리면 잘못된 비밀번호입니다.
        맞으면 새 비밀번호 DB 업데이트
     */
    @PostMapping("/myPage/edit")
    public ResponseEntity myPageEditSubmit(@RequestBody EditMyPageResponse editMyPageRes) {

        String msg1 = "success";
        String msg2 = "success";

        if (editMyPageRes.getNewNickname() != null) {
            // 닉네임 변경, 성공 = success, 이미 존재하는 닉네임 = fail
            msg1 = myPageService.editNickname(editMyPageRes.getUserEmail(), editMyPageRes.getNewNickname());
        }
        if (editMyPageRes.getNewPassword() != null) {
            // 비밀번호 변경, 성공 = success, 잘못된 비밀번호 = fail
            msg2 = myPageService.editPassword(editMyPageRes.getUserEmail(), editMyPageRes.getUserPassword(), editMyPageRes.getNewPassword());
        }

        EditMyPageResult result = new EditMyPageResult(msg1, msg2); // ex) {"nickname" : "success", "password" : "fail"}

        if (msg1.equals("success") && msg2.equals("success"))
            return ResponseEntity.ok().body(result);

        return ResponseEntity.badRequest().body(result);
    }

    /*
        회원 탈퇴

        DB에 해당 사용자 제거, 세션 제거
     */
    @PostMapping("/myPage/edit/withdraw")
    public ResponseEntity myPageMemberWithdraw(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        Member findMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        String msg = myPageService.withdrawMember(findMember);

        session.invalidate(); // 세션 삭제

        if (msg.equals("success"))
            return ResponseEntity.ok().body(msg); // 성공

        return ResponseEntity.badRequest().body(msg);
    }

}
