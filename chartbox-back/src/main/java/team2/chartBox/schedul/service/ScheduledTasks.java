package team2.chartBox.schedul.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team2.chartBox.schedul.service.MovieChartService;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class ScheduledTasks {

    @Autowired
    public MovieChartService movieChartService;

    /*
        영화 예매 순위
    */
    @Scheduled(cron = "0 59 23 * * *") // 매일 23시 59분 업데이트
    public void movieBookingChartTasks() throws IOException {
        movieChartService.movieBookingChart();
        log.info("예매차트 업데이트");
    }

    /*
        박스 오피스
     */
    @Scheduled(cron = "0 59 23 ? * SUN") // 매주 일요일 23시 59분
    public void boxOfficeChartTasks() throws IOException {
        movieChartService.boxOfficeChart();
        log.info("박스오피스 업데이트");
    }
}
