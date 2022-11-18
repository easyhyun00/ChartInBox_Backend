package team2.chartBox.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieApiClient movieApiClient;

    public MovieResponseDto findByKeyword(String keyword) {
        return movieApiClient.requestMovie(keyword);
    }

}
