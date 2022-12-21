package team2.chartBox.movieApi.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team2.chartBox.movieApi.dto.MvApiDto;
import team2.chartBox.movieApi.service.FindMvService;

import java.io.IOException;

@RestController
@Slf4j
@AllArgsConstructor
public class MvApiController {

    private final FindMvService findMvService;

    @GetMapping("/mv/title/{mvTitle}") // KMDb - 이름 검색
    public void mvInfo(@PathVariable String mvTitle) throws IOException, ParseException {
        findMvService.findByMvTitle(mvTitle);
    }

    @GetMapping("/mv/id/{mvId}") // KMDb - movieId 검색
    public MvApiDto mvInfo2(@PathVariable String mvId) throws IOException, ParseException {
        MvApiDto test = findMvService.findByMvId(mvId);
        return test;
    }
}
