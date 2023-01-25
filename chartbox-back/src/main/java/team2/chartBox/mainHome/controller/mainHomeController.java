package team2.chartBox.mainHome.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team2.chartBox.curation.dto.CurationDto;
import team2.chartBox.curation.dto.CurationResponse;
import team2.chartBox.curation.service.CurationService;
import team2.chartBox.freeBoard.dto.MovieTalkDto;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.mainHome.dto.MainHomeDto;
import team2.chartBox.mainHome.service.MainHomeService;
import team2.chartBox.nPartyBoard.dto.NPartyBoardListDetailDto;
import team2.chartBox.nPartyBoard.service.NPartyBoardService;
import team2.chartBox.schedul.service.MovieChartService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class MainHomeController {

    @Autowired
    private MainHomeService mainHomeService;
    private FreeBoardService freeBoardService;
    private NPartyBoardService nPartyBoardService;
    private CurationService curationService;

    @GetMapping("/") // 메인 홈
    public ResponseEntity<MainHomeDto> home() throws IOException {
        MainHomeDto mainHomeDto = new MainHomeDto();
        mainHomeDto.setMvCharts(mainHomeService.getMvChartList());

        // 큐레이션 - 개추운날
        CurationResponse curationData = curationService.getCurationCold();
        mainHomeDto.setCurationInfo(curationData.getCurationInfo());

        List<CurationDto> curationMovie = curationService.getCurationCategory("개추운날");
        mainHomeDto.setCurationList(curationMovie.subList(0,3));

        List<MovieTalkDto> freeBoardList = freeBoardService.getFreeBoardList();
        if (freeBoardList.size() > 10)
            freeBoardList.subList(0,10);
        mainHomeDto.setFreeBoardList(freeBoardList);

        List<MovieTalkDto> reviewBoardList = freeBoardService.getReviewBoardList();
        if (reviewBoardList.size() > 10)
            reviewBoardList.subList(0,10);
        mainHomeDto.setReviewBoardList(reviewBoardList);

        List<NPartyBoardListDetailDto> nPartyBoardList = nPartyBoardService.getNPartyList();
        if (nPartyBoardList.size() > 10)
            nPartyBoardList.subList(0,10);
        mainHomeDto.setNPartyBoardList(nPartyBoardList);

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(mainHomeDto,headers, HttpStatus.OK);
    }
}
