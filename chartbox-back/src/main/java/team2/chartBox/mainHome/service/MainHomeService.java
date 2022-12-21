package team2.chartBox.mainHome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.schedul.repository.MovieChartRepository;
import team2.chartBox.schedul.entity.MovieChart;

import java.util.List;

@Service
public class MainHomeService {
    @Autowired
    public MovieChartRepository movieChartRepository;

    public List<MovieChart> getMvChartList() {
        return movieChartRepository.findAll();
    }

}
