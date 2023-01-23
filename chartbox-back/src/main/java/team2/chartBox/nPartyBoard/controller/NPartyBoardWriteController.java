package team2.chartBox.nPartyBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.member.entity.Member;
import team2.chartBox.nPartyBoard.dto.BoardWriteDto;
import team2.chartBox.nPartyBoard.service.NPartyBoardService;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class NPartyBoardWriteController {

    @Autowired
    private NPartyBoardService nPartyBoardService;

    /*
        게시물 작성
        성공 - “success”
        실패 - “non-member’ - 없는 회원
     */
    @PostMapping("/n-party/write")
    public ResponseEntity NPartyWrite(@RequestBody BoardWriteDto boardWriteDto,
                                      @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        String strMsg = nPartyBoardService.PostWrite(boardWriteDto, member);
        return getResponseEntity(strMsg);
    }

    /*
        게시물 수정 페이지
        성공 - "success"
        실패 - "pathVariable" 잘못된 경로, "board" 없는 글, "non-member" 없는 회원, "member" 잘못된 유저
     */
    @GetMapping("/n-party/{postId}/edit")
    public ResponseEntity NPartyEditPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                         @PathVariable(required = false) String postId) {

        String strMsg = nPartyBoardService.EditPage(postId, member);
        if (strMsg == "success") {
            BoardWriteDto boardWriteDto = new BoardWriteDto(nPartyBoardService.getBoardDetail(postId));
            return ResponseEntity.ok().body(boardWriteDto);
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

    /*
        게시물 수정 제출
        성공 - "success"
        실패 - "pathVariable" 잘못된 경로, "board" 없는 글, "non-member" 없는 회원, "member" 잘못된 유저
     */
    @PostMapping("/n-party/{postId}/edit")
    public ResponseEntity NPartyEdit(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                     @PathVariable(required = false) String postId, @RequestBody BoardWriteDto boardWriteDto) {
        String strMsg = nPartyBoardService.EditBoard(postId, boardWriteDto, member);
        return getResponseEntity(strMsg);
    }

    /*
        게시물 삭제
     */
    @PostMapping("/n-party/{postId}/delete")
    public ResponseEntity NPartyDelete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                       @PathVariable(required = false) String postId) {
        String strMsg = nPartyBoardService.DeleteBoard(postId, member);
        return getResponseEntity(strMsg);
    }

    /*
        게시물 좋아요
     */
    @PostMapping("n-party/{postId}/like")
    public ResponseEntity NPartyLike(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                     @PathVariable(required = false) String postId) throws ParseException {
        String strMsg = nPartyBoardService.LikeBoard(postId, member);
        if (strMsg == "success" || strMsg == "cancel") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }


    /*
        게시물 신고
     */
    @PostMapping("n-party/{postId}/report")
    public ResponseEntity NPartyReport(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                       @PathVariable(required = false) String postId) throws ParseException {
        String strMsg = nPartyBoardService.ReportBoard(postId, member);
        if (strMsg == "success" || strMsg == "overlap" || strMsg == "delete") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

    /*
        리턴 값
     */
    private ResponseEntity<String> getResponseEntity(String strMsg) {
        if (strMsg == "success") {
            return ResponseEntity.ok().body(strMsg);
        } else {
            return ResponseEntity.badRequest().body(strMsg);
        }
    }
}
