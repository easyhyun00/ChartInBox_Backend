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
import team2.chartBox.mainHome.dto.MainHomeDto;
import team2.chartBox.mainHome.service.MainHomeService;
import team2.chartBox.schedul.service.MovieChartService;

import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class MainHomeController {

    @Autowired
    private MainHomeService mainHomeService;
    private MovieChartService movieChartService;

    @GetMapping("/")
    public ResponseEntity<MainHomeDto> home() throws IOException {
        MainHomeDto mainHomeDto = new MainHomeDto();
        mainHomeDto.setMvCharts(mainHomeService.getMvChartList());

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));


        log.info("원래==> {}",mainHomeDto);



        return new ResponseEntity<>(mainHomeDto,headers, HttpStatus.OK);
    }
}
