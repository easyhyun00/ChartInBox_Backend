package team2.chartBox.schedul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.chartBox.schedul.entity.MovieChart;

public interface MovieChartRepository extends JpaRepository<MovieChart, Long> {
    MovieChart findByMvChartId(Integer mvChartId);
    MovieChart findTop1ByMvChartName(String mvChartName);
}