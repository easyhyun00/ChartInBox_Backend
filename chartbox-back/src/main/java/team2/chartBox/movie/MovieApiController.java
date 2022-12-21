package team2.chartBox.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MovieApiController {

    private final MovieService movieService;

    @GetMapping("/movie/search/{keyword}") // 네이버 영화
    public MovieResponseDto get(@PathVariable String keyword) {
        return movieService.findByKeyword(keyword);
    }

    @GetMapping("/test") // 영화 차트
    public void test() throws Exception {
        CgvTest.WeeklyBoxOfficeChart();
    }
}
