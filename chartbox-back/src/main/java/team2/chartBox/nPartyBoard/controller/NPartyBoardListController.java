package team2.chartBox.nPartyBoard.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.member.entity.Member;
import team2.chartBox.nPartyBoard.dto.NPartyBoardDetailDto;
import team2.chartBox.nPartyBoard.dto.NPartyBoardListDto;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;
import team2.chartBox.nPartyBoard.service.NPartyBoardService;

import java.nio.charset.Charset;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class NPartyBoardListController {

    @Autowired
    private NPartyBoardService nPartyBoardService;

    /*
        N팟 구함 - 전체 글 목록
     */
    @GetMapping("/n-party")
    public ResponseEntity<NPartyBoardListDto> NPartyTotalPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {

        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyList());

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 넷플릭스 글 목록
     */
    @GetMapping("/n-party/netflix")
    public ResponseEntity<NPartyBoardListDto> NPartyNetflixPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyCategoryList("넷플릭스"));

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 왓챠 글 목록
     */
    @GetMapping("/n-party/watcha")
    public ResponseEntity<NPartyBoardListDto> NPartyWatchaPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyCategoryList("왓챠"));

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 티빙 글 목록
     */
    @GetMapping("/n-party/tving")
    public ResponseEntity<NPartyBoardListDto> NPartyTvingPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyCategoryList("티빙"));

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 웨이브 글 목록
     */
    @GetMapping("/n-party/wavve")
    public ResponseEntity<NPartyBoardListDto> NPartyWavvePage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyCategoryList("웨이브"));

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 디즈니 글 목록
     */
    @GetMapping("/n-party/disney")
    public ResponseEntity<NPartyBoardListDto> NPartyDisneyPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyCategoryList("디즈니"));

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 기타 글 목록
     */
    @GetMapping("/n-party/etc")
    public ResponseEntity<NPartyBoardListDto> NPartyEtcPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoardListDto nPartyBoardListDto = new NPartyBoardListDto();
        nPartyBoardListDto.setBoardList(nPartyBoardService.getNPartyCategoryList("기타"));

        return getResponseEntity(member, nPartyBoardListDto);
    }

    /*
        N팟 구함 - 글 상세 보기
     */
    @GetMapping("/n-party/{postId}")
    public ResponseEntity<NPartyBoardDetailDto> FreeBoardDetailPage(@PathVariable String postId,
                                                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        NPartyBoard boardDetail = nPartyBoardService.getBoardDetail(postId); // 글
        nPartyBoardService.PostViewCnt(boardDetail); // 조회수 증가

        NPartyBoardDetailDto nPartyBoardDetailDto = new NPartyBoardDetailDto();

        nPartyBoardDetailDto.setPostDetail(boardDetail); // 게시물
        nPartyBoardDetailDto.setComments(nPartyBoardService.getCommentList(postId)); // 댓글
        nPartyBoardDetailDto.setPostList(nPartyBoardService.getNPartyList()); // 게시물 목록

        if (member == null) {
            nPartyBoardDetailDto.setUserNickname("");
        } else {
            nPartyBoardDetailDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(nPartyBoardDetailDto, headers, HttpStatus.OK);

    }
    /*
        글 목록 리턴 값
     */
    private ResponseEntity<NPartyBoardListDto> getResponseEntity(Member member, NPartyBoardListDto nPartyBoardListDto) {
        if (member == null) {
            nPartyBoardListDto.setUserNickname("");
        } else {
            nPartyBoardListDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(nPartyBoardListDto, headers, HttpStatus.OK);
    }
}
