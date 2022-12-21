package team2.chartBox.movieApi.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import team2.chartBox.movieApi.dto.MvApiDto;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FindMvService {

    private final MvApiService mvApiService;

    public void findByMvTitle(String mvTitle) throws IOException, ParseException {
        mvApiService.requestMovie(mvTitle);
    }

    public MvApiDto findByMvId(String mvId) throws IOException, ParseException {
        return mvApiService.requestMovieById(mvId);
    }
}
