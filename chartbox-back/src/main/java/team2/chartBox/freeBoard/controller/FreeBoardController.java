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
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;

import java.nio.charset.Charset;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;

    /*
        게시판 글 목록
     */
    @GetMapping("/free-board")
    public ResponseEntity<FreeBoardDto> FreeBoardTotalPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setFreeBoardList(freeBoardService.getFreeBoardList());

        if (member == null) {
            freeBoardDto.setUserNickname("비회원");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

    /*
        게시판 상세 보기
     */
    @GetMapping("/free-board/{postId}")
    public ResponseEntity<FreeBoardDetailDto> FreeBoardDetailPage(@PathVariable String postId,
                                                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        FreeBoard freeBoardDetail = freeBoardService.getFreeBoardDetail(postId);

        FreeBoardDetailDto freeBoardDetailDto = new FreeBoardDetailDto();
        freeBoardDetailDto.setFreeBoard(freeBoardDetail);

        if (member == null) {
            freeBoardDetailDto.setUserNickname("비회원");
        } else {
            freeBoardDetailDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDetailDto,headers, HttpStatus.OK);
    }

}
