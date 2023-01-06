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
import team2.chartBox.member.entity.Member;
import team2.chartBox.movieApi.dto.MvApiDto;
import team2.chartBox.movieApi.dto.MvDetailDto;
import team2.chartBox.movieApi.service.FindMvService;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@Slf4j
@AllArgsConstructor
public class MvApiController {

    @Autowired
    private FindMvService findMvService;

    @GetMapping("/mv/title/{mvTitle}") // KMDb - 이름 검색
    public void mvInfo(@PathVariable String mvTitle) throws IOException, ParseException {
        findMvService.findByMvTitle(mvTitle);
    }

    /*
        영화 상세보기
     */
    @GetMapping("/movie-info/{mvId}") // KMDb - movieId 검색
    public ResponseEntity<MvDetailDto> mvInfo2(@PathVariable String mvId,
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

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(mvDetailDto,headers, HttpStatus.OK);
    }

    /*
        영화 스크랩 하기
     */
    @PostMapping("/movie-info/{mvId}/scrap")
    public void movieScrap(@PathVariable String mvId,
                           @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws ParseException {
        boolean scrapState = findMvService.movieClipping(member.getUserNickname(), mvId);

    }
}
