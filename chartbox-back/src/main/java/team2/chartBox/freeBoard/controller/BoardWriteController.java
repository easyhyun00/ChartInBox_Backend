package team2.chartBox.freeBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.freeBoard.dto.BoardWriteDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class BoardWriteController {

    @Autowired
    private FreeBoardService freeBoardService;
    @Autowired
    private FreeBoardRepository freeBoardRepository;

    /*
        무비 토크 - 게시물 작성(회원만 작성할 수 있도록)

        프론트로 받는 데이터 =>
        postTitle; 제목
        postContent; 내용
        postUserNickname; 작성자
        postCategory; 카테고리(자유,리뷰,Q&A)
        postSpoiler; 스포일러(기본 false,true)
        postAnonym; 익명(기본 false, true)
     */
    @PostMapping("/movie-talk/write")
    public boolean MovieTalkWrite(@RequestBody BoardWriteDto boardWriteDto,
                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        boardWriteDto.setPostUserNickname(member.getUserNickname());

        // dto 형식 바꾸고 db에 저장
        FreeBoard freeBoard = new FreeBoard(boardWriteDto);
        freeBoardRepository.save(freeBoard);

        return true;
    }

    /*
        무비 토크 - 게시물 수정 페이지
     */
    @GetMapping("/movie-talk/{postId}/edit")
    public ResponseEntity MovieTalkEditPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
                                            @PathVariable(required = false) String postId) {

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

}
