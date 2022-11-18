package team2.chartBox.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MovieApiController {

    private final MovieService movieService;

    @GetMapping("/movie/search/{keyword}")
    public MovieResponseDto get(@PathVariable String keyword) {
        return movieService.findByKeyword(keyword);
    }
}
