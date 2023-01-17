package team2.chartBox.freeBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.freeBoard.dto.CommentDto;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    @Autowired
    public FreeBoardService freeBoardService;

    /*
        댓글 작성
     */
    @PostMapping("/movie-talk/{postId}/comment")
    public ResponseEntity MovieTalkCommentWrite(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                                @PathVariable(required = false) String postId, @RequestBody CommentDto commentDto) {

        String strMsg = freeBoardService.CommentAdd(postId, commentDto, member);
        if (strMsg == "success") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

    /*
        댓글 수정
     */
    @PostMapping("/movie-talk/{postId}/comment/{cmtId}/edit")
    public ResponseEntity MovieTalkCommentEdit(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                               @PathVariable(required = false) String postId, @PathVariable(required = false) String cmtId,
                                               @RequestBody CommentDto commentDto) {
        String strMsg = freeBoardService.CommentEdit(postId, cmtId, commentDto, member);
        if (strMsg == "success") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }

    /*
        댓글 삭제
     */
    @PostMapping("/movie-talk/{postId}/comment/{cmtId}/delete")
    public ResponseEntity MovieTalkCommentDelete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                                 @PathVariable(required = false) String postId, @PathVariable(required = false) String cmtId) {
        String strMsg = freeBoardService.CommentDelete(postId, cmtId, member);
        if (strMsg == "success") {
            return ResponseEntity.ok().body(strMsg); // 성공
        } else {
            return ResponseEntity.badRequest().body(strMsg); // 실패
        }
    }
}
