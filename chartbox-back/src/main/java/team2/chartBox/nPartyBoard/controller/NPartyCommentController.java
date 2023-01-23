package team2.chartBox.nPartyBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.member.entity.Member;
import team2.chartBox.nPartyBoard.dto.CommentDto;
import team2.chartBox.nPartyBoard.service.NPartyBoardService;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class NPartyCommentController {

    @Autowired
    public NPartyBoardService nPartyBoardService;

    /*
        댓글 작성
     */
    @PostMapping("/n-party/{postId}/comment")
    public ResponseEntity nPartyCmtWrite(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                         @PathVariable(required = false) String postId, @RequestBody CommentDto commentDto){
        String strMsg = nPartyBoardService.AddComment(postId, commentDto, member);
        return getResponseEntity(strMsg);
    }


    /*
        댓글 수정
     */
    @PostMapping("/n-party/{postId}/comment/{cmtId}/edit")
    public ResponseEntity nPartyCmtEdit(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                         @PathVariable(required = false) String postId, @PathVariable(required = false) String cmtId,
                                         @RequestBody CommentDto commentDto){
        String strMsg = nPartyBoardService.EditComment(postId, cmtId, commentDto, member);
        return getResponseEntity(strMsg);
    }

    /*
        댓글 삭제
     */
    @PostMapping("/n-party/{postId}/comment/{cmtId}/delete")
    public ResponseEntity nPartyCmtDelete(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                         @PathVariable(required = false) String postId, @PathVariable(required = false) String cmtId){
        String strMsg = nPartyBoardService.DeleteComment(postId, cmtId, member);
        return getResponseEntity(strMsg);
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
