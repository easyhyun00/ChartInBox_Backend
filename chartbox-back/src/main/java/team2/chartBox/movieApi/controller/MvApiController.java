package team2.chartBox.movieApi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.SessionConst;
import team2.chartBox.freeBoard.dto.BoardWriteDto;
import team2.chartBox.freeBoard.dto.MovieTalkDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;
import team2.chartBox.movieApi.dto.*;
import team2.chartBox.movieApi.service.FindMvService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MvApiController {

    @Autowired
    private FindMvService findMvService;
    private FreeBoardService freeBoardService;
    private FreeBoardRepository freeBoardRepository;

    /*
        영화 검색 페이지
     */
    @GetMapping("/movie-search/{mvTitle}")
    public MvSearchDto movieSearchPage(@PathVariable String mvTitle) throws IOException, ParseException {
        log.info(mvTitle);

        MvSearchDto mvSearchDto = new MvSearchDto();
        mvSearchDto.setSearchTitle(mvTitle);
        mvSearchDto.setSearchList(findMvService.findByMvTitle(mvTitle));

        return mvSearchDto;
    }

    /*
        영화 상세보기
     */
    @GetMapping("/movie-info/{mvId}") // KMDb - movieId 검색
    public ResponseEntity<MvDetailDto> mvInfoDetail(@PathVariable String mvId,
                                               @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws IOException, ParseException {

        MvDetailDto mvDetailDto = new MvDetailDto();

        MvApiDto mvApiDto = findMvService.findByMvId(mvId);

        if (member == null) { // 비회원
            mvDetailDto.setMovieScrap(false);
        } else { // 회원
            boolean scrapState = findMvService.movieClipState(member.getUserNickname(), mvId);
            mvDetailDto.setMovieScrap(scrapState);
        }

        mvDetailDto.setMovieDetail(mvApiDto);

        mvDetailDto.setCurationList(findMvService.curationList());

        List<MovieTalkDto> reviewBoardList = freeBoardService.getReviewBoardList();
        if (reviewBoardList.size() > 10)
            reviewBoardList.subList(0,10);
        mvDetailDto.setReviewBoardList(reviewBoardList);

        List<MovieTalkDto> qnaBoardList = freeBoardService.getQnaBoardList();
        if (qnaBoardList.size() > 10)
            qnaBoardList.subList(0,10);
        mvDetailDto.setQnaBoardList(qnaBoardList);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(mvDetailDto,headers, HttpStatus.OK);
    }

    /*
        영화 상세에서 글쓰기
     */
    @PostMapping("movie-info/{mvId}/write")
    public ResponseEntity movieInfoWrite(@PathVariable String mvId, @RequestBody BoardWriteDto boardWriteDto,
                                         @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
        if (member == null)
            return ResponseEntity.badRequest().body("non-member");

        boardWriteDto.setPostUserNickname(member.getUserNickname());
        boardWriteDto.setMovieId(mvId);

        FreeBoard freeBoard = new FreeBoard(boardWriteDto);
        freeBoardRepository.save(freeBoard);

        return ResponseEntity.ok().body("success");
    }

    /*
        영화 스크랩 하기
     */
    @PostMapping("/movie-info/{mvId}/scrap")
    public ResponseEntity movieScrap(@PathVariable String mvId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws ParseException {

        if (member == null) {
            return ResponseEntity.badRequest().body(false);
        }

        String scrapState = findMvService.movieClipping(member.getUserNickname(), mvId);

        return ResponseEntity.ok().body(scrapState);
    }

    /*
        영화 작품 탐색 페이지
        @RequestParam
        파라미터 아무것도 안 줄 때 startCount=0 ~ startCount=493 매번 랜덤 데이터
     */
    @GetMapping("/movie-explore")
    public ResponseEntity movieExplore2(String genre, String nation, String year) throws IOException, ParseException {
        log.info(genre); // 장르
        log.info(nation);
        log.info(year);
        List<MvScrapDto> list = findMvService.findByMvFilter(genre, nation, year);
        if (genre == null && nation == null && year == null) {
            MvExploreDto mvExploreDto = new MvExploreDto();
            mvExploreDto.setMovieExploreList(list);
            List<MvCurationDto> curationList = findMvService.curationList();
            mvExploreDto.setCurationList(curationList);
            return ResponseEntity.ok().body(mvExploreDto);
        }
        return ResponseEntity.ok().body(list);
    }
}
