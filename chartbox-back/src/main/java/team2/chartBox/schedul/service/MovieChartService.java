package team2.chartBox.schedul.service;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.schedul.repository.MovieChartRepository;
import team2.chartBox.schedul.entity.MovieChart;

import java.io.IOException;

@Service
@AllArgsConstructor
public class MovieChartService {

    @Autowired
    public MovieChartRepository movieChartRepository;

    /*
        영화 예매 차트
     */
    public void movieBookingChart() throws IOException {

        Document doc = Jsoup.connect("https://movie.daum.net/ranking/reservation").get();

        Elements element = doc.select("div.box_ranking");

        Elements images = element.select("img.img_thumb");
        Elements ages = element.select("span.txt_tag");
        Elements titles = element.select("a.link_txt");

        for (int i = 0; i < 10; i++) {
            int ranking = (i + 1);
            String mv_image = images.get(i).attr("src"); // 포스터
            String mv_age = ages.get(i).text(); // 관람 연령
            String mv_title = titles.get(i).text(); // 제목

            System.out.println("=======>1");

            MovieChart findMvChart = movieChartRepository.findByMvChartId(i+1);
            findMvChart.setMvChartName(mv_title);
            findMvChart.setMvChartAge(mv_age);
            findMvChart.setMvChartImg(mv_image);

            System.out.println("=======>2");

            MovieChart findMvName = movieChartRepository.findTop1ByMvChartName(mv_title);
            if ((findMvName != null)) {
                findMvChart.setMovieId(findMvName.getMovieId());
            } else {
                findMvChart.setMovieId(null);
            }

            System.out.println("=======>3");

            movieChartRepository.save(findMvChart);
        }

    }

    /*
        박스 오피스 주간 차트
    */
    public void boxOfficeChart() throws IOException {
        Document doc = Jsoup.connect("https://movie.daum.net/ranking/boxoffice/weekly").get();

        Elements element = doc.select("div.box_boxoffice");

        Elements images = element.select("img.img_thumb");
        Elements ages = element.select("span.txt_tag");
        Elements titles = element.select("a.link_txt");

        for (int i = 0; i < 10; i++) {
            String mv_image = images.get(i).attr("src");
            String mv_age = ages.get(i).text();
            String mv_title = titles.get(i).text();

            MovieChart findMvChart = movieChartRepository.findByMvChartId(i + 31);
            findMvChart.setMvChartName(mv_title);
            findMvChart.setMvChartAge(mv_age);
            findMvChart.setMvChartImg(mv_image);

            MovieChart findMvName = movieChartRepository.findTop1ByMvChartName(mv_title);
            if ((findMvName != null)) {
                findMvChart.setMovieId(findMvName.getMovieId());
            } else {
                findMvChart.setMovieId(null);
            }

            movieChartRepository.save(findMvChart);

        }
    }

    /*
        OTT 차트
        키노라이츠, 셀레니움으로 크롤링
    */
}
