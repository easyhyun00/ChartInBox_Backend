package team2.chartBox.freeBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.freeBoard.dto.FreeBoardDetailDto;
import team2.chartBox.freeBoard.dto.FreeBoardDto;
import team2.chartBox.freeBoard.dto.PostDetailDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;

import java.nio.charset.Charset;
import java.time.LocalDateTime;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;

    /*
        무비 토크 - 전체 글 목록
     */
    @GetMapping("/movie-talk")
    public ResponseEntity<FreeBoardDto> MovieTalkTotalPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getMovieTalkList());

        if (member == null) {
            freeBoardDto.setUserNickname("");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

    /*
        무비 토크 - 자유 게시판 글 목록
     */
    @GetMapping("/movie-talk/free-board")
    public ResponseEntity<FreeBoardDto> MovieTalkFreeBoardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getFreeBoardList());

        if (member == null) {
            freeBoardDto.setUserNickname("");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

    /*
        무비 토크 - 리뷰게시판 글 목록
     */
    @GetMapping("/movie-talk/review-board")
    public ResponseEntity<FreeBoardDto> MovieTalkReviewBoardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getReviewBoardList());

        if (member == null) {
            freeBoardDto.setUserNickname("");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

    /*
       무비 토크 - Q&A 글 목록
    */
    @GetMapping("/movie-talk/qna-board")
    public ResponseEntity<FreeBoardDto> MovieTalkQnaBoardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getQnaBoardList());

        if (member == null) {
            freeBoardDto.setUserNickname("");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

    /*
        무비 토크 - 게시물 상세 보기
     */
    @GetMapping("/movie-talk/{postId}")
    public ResponseEntity<FreeBoardDetailDto> FreeBoardDetailPage(@PathVariable String postId,
                                                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoard freeBoardDetail = freeBoardService.getFreeBoardDetail(postId);

        freeBoardService.PostViewCnt(freeBoardDetail); // 조회수 증가

        PostDetailDto postDetailDto = new PostDetailDto(freeBoardDetail);

        FreeBoardDetailDto freeBoardDetailDto = new FreeBoardDetailDto();
        freeBoardDetailDto.setPostDetail(postDetailDto);
        freeBoardDetailDto.setComments(freeBoardService.getCommentList(postId));
        freeBoardDetailDto.setPostList(freeBoardService.getMovieTalkList());

        if (member == null) {
            freeBoardDetailDto.setUserNickname("");
        } else {
            freeBoardDetailDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDetailDto,headers, HttpStatus.OK);
    }

}
