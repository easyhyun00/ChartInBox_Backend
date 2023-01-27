package team2.chartBox.freeBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.EmailConst;
import team2.chartBox.SessionConst;
import team2.chartBox.freeBoard.dto.BoardWriteDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class BoardWriteController {

    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private FreeBoardRepository freeBoardRepository;
    private MemberRepository memberRepository;

//    /*
//        무비 토크 - 게시물 작성
//        다시~!
//        성공 - “success”
//        실패 - “non-member’ - 없는 회원(비회원)
//     */
//    @PostMapping("/movie-talk/write")
//    public ResponseEntity MovieTalkWrite(@RequestBody BoardWriteDto boardWriteDto,
//                                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
//
//
//        if (member == null)
//            return ResponseEntity.badRequest().body("non-member");
//
//        boardWriteDto.setPostUserNickname(member.getUserNickname());
//
//        // dto 형식 바꾸고 db에 저장
//        FreeBoard freeBoard = new FreeBoard(boardWriteDto);
//        freeBoardRepository.save(freeBoard);
//
//        return ResponseEntity.ok().body("success");
//    }

    /*
    무비 토크 - 게시물 작성2
    다시~!
    성공 - “success”
    실패 - “non-member’ - 없는 회원(비회원)
    */
    @PostMapping("/movie-talk/write")
    public ResponseEntity MovieTalkWrite(@RequestBody BoardWriteDto boardWriteDto) {

        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        if (member == null)
            return ResponseEntity.badRequest().body("non-member");

        boardWriteDto.setPostUserNickname(member.getUserNickname());

        // dto 형식 바꾸고 db에 저장
        FreeBoard freeBoard = new FreeBoard(boardWriteDto);
        freeBoardRepository.save(freeBoard);

        return ResponseEntity.ok().body("success");
    }

//    /*
//        무비 토크 - 게시물 수정 페이지
//     */
//    @GetMapping("/movie-talk/{postId}/edit")
//    public ResponseEntity MovieTalkEditPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
//                                            @PathVariable(required = false) String postId) {
//
//        String strMsg = freeBoardService.EditBoard(postId, member);
//        if (strMsg == "success") {
//            BoardWriteDto boardWriteDto = new BoardWriteDto(freeBoardService.getFreeBoardDetail(postId));
//            return ResponseEntity.ok().body(boardWriteDto); // 성공
//        } else {
//            return ResponseEntity.badRequest().body(strMsg); // 실패
//        }
//    }

    /*
        무비 토크 - 게시물 수정 페이지 2
    */
    @GetMapping("/movie-talk/{postId}/edit")
    public ResponseEntity MovieTalkEditPage(@PathVariable(required = false) String postId) {

        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        String strMsg = freeBoardService.EditBoard(postId, member);
        if (strMsg == "success") {
            BoardWriteDto boardWriteDto = new BoardWriteDto(freeBoardService.getFreeBoardDetail(postId));
            return ResponseEntity.ok().body(boardWriteDto); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

    /*
        무비 토크 - 게시물 수정
     */
    @PostMapping("/movie-talk/{postId}/edit")
    public ResponseEntity MovieTalkEdit(@PathVariable(required = false) String postId, @RequestBody BoardWriteDto boardWriteDto,
                                        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        String strMsg = freeBoardService.EditBoardSubmit(postId, boardWriteDto, member);
        if (strMsg == "success") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

    /*
        무비 토크 - 게시물 삭제
     */
    @PostMapping("movie-talk/{postId}/delete")
    public ResponseEntity MovieTalkDelete(@PathVariable(required = false) String postId,
                                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        String strMsg = freeBoardService.DeleteBoardSubmit(postId,member);
        if (strMsg == "success") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

//    /*
//        무비 토크 - 게시물 좋아요
//
//        비회원 - 좋아요 불가
//        회원 - 좋아요
//        회원 - 이미 좋아요 누름 -> 좋아요 취소
//
//     */
//    @PostMapping("movie-talk/{postId}/like")
//    public ResponseEntity MovieTalkLike(@PathVariable(required = false) String postId,
//                                 @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws ParseException {
//
//        String strMsg = freeBoardService.PostLike(postId, member);
//
//        if (strMsg == "success" || strMsg == "cancel") {
//            return ResponseEntity.ok().body(strMsg); // 성공
//        } else {
//            return ResponseEntity.badRequest().body(strMsg); // 실패
//        }
//    }

    /*
    무비 토크 - 게시물 좋아요2

    비회원 - 좋아요 불가
    회원 - 좋아요
    회원 - 이미 좋아요 누름 -> 좋아요 취소

 */
    @PostMapping("movie-talk/{postId}/like")
    public ResponseEntity MovieTalkLike(@PathVariable(required = false) String postId) throws ParseException {

        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        String strMsg = freeBoardService.PostLike(postId, member);

        if (strMsg == "success" || strMsg == "cancel") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

//    /*
//        무비 토크 - 게시물 신고
//
//        비회원 - 신고 불가
//        회원 - 신고
//        회원 - 이미 신고 -> 신고 불가
//        신고 5개 이상 -> 자동 글 삭제제
//     */
//    @PostMapping("movie-talk/{postId}/report")
//    public ResponseEntity MovieTalkReport(@PathVariable(required = false) String postId,
//                                          @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws ParseException {
//
//        String strMsg = freeBoardService.PostReport(postId, member);
//
//        if (strMsg == "success" || strMsg == "overlap" || strMsg == "delete") {
//            return ResponseEntity.ok().body(strMsg); // 성공
//        } else {
//            return ResponseEntity.badRequest().body(strMsg); // 실패
//        }
//
//    }

    /*
    무비 토크 - 게시물 신고2

    비회원 - 신고 불가
    회원 - 신고
    회원 - 이미 신고 -> 신고 불가
    신고 5개 이상 -> 자동 글 삭제제
 */
    @PostMapping("movie-talk/{postId}/report")
    public ResponseEntity MovieTalkReport(@PathVariable(required = false) String postId) throws ParseException {

        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        String strMsg = freeBoardService.PostReport(postId, member);

        if (strMsg == "success" || strMsg == "overlap" || strMsg == "delete") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }

    }

}
