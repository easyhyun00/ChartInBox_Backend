package team2.chartBox.movie;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
public class CgvTest {

    /*
        CGV 예매율 순위 1~10
     */
    public static void CgvChart() throws Exception {

        log.info("CGV 영화 순위");

        Document doc = Jsoup.connect("http://www.cgv.co.kr/movies/?lt=1&ft=1").get();

        Elements element = doc.select("div.sect-movie-chart");

        Elements titles = element.select("strong.title");
        Elements ranks = element.select("strong.rank");
        Elements img = element.select("span.thumb-image img");

        for (int i = 0 ; i < 10; i++) {
            String mv_title = titles.get(i).text();
            String mv_rank = ranks.get(i).text();
            String mv_img = img.get(i).attr("src");
            System.out.println(mv_rank+ " " + mv_title + " " + mv_img);
        }

    }

    /*
        네이버 영화 순위
     */
    public static void NaverChart() throws Exception {

        log.info("네이버 영화 순위");

        Document doc = Jsoup.connect("https://movie.naver.com/movie/sdb/rank/rmovie.naver").get();

        Elements elements = doc.select("table.list_ranking");

        Elements titles = elements.select("div.tit3 a");

        for (int i = 0; i < 10; i++) {
            String mv_rank = "No." + (i + 1);
            String mv_title = titles.get(i).text();
            String mv_img = getMvImg(titles.get(i).attr("href"));

            System.out.println(mv_rank + " " + mv_title + " " + mv_img);
        }

    }

    /*
        https://movie.naver.com/movie/bi/mi/photoViewPopup.naver?movieCode=184516
        네이버 영화 순위 포스터 가져오기
     */
    private static String getMvImg(String href) {
        String src = "https://movie.naver.com/movie/bi/mi/photoViewPopup.naver?movieCode=";

        String code = href.substring(href.lastIndexOf("=") + 1);

        return src + code;
    }

    /*
        왓챠피디아 - 왓챠 TOP10
     */
    public static void WatchaChart() throws Exception {

        log.info("왓챠 영화 순위");

        Document doc = Jsoup.connect("https://pedia.watcha.com/ko-KR/?domain=movie").get();

        Element element = doc.select("ul.css-yqjebm-VisualUl").get(1);

        Elements titles = element.select("div.css-5yuqaa");

        Elements img = element.select("div.css-1qmeemv img");

        for (int i = 0; i < 10; i++) {
            String mv_rank = "No." + (i + 1);
            String mv_title = titles.get(i).text();
            String mv_img = img.get(i).attr("src");

            System.out.println(mv_rank + " " + mv_title + " " + mv_img);
        }
    }

    public static void WavveChart() throws IOException {

        Document doc = Jsoup.connect("https://www.wavve.com/supermultisection/GN59").get();

        System.out.println(doc);
    }

    public static void daumChart() throws IOException {
        Document doc = Jsoup.connect("https://movie.daum.net/ranking/reservation").get();

        Elements element = doc.select("div.box_ranking");

        Elements images = element.select("img.img_thumb");
        Elements ages = element.select("span.txt_tag");
        Elements titles = element.select("a.link_txt");
//        Elements scores = element.select("span.txt_append");
//        Elements dates = element.select("span.txt_info");


        for (int i = 0; i < 10; i++) {
            int ranking = (i + 1);
            String mv_image = images.get(i).attr("src");
            String mv_age = ages.get(i).text();
            String mv_title = titles.get(i).text();
//            String mv_score = scores.get(i).select("span.txt_grade").text();
//            String mv_percent = scores.get(i).select("span.txt_num").text();
//            String mv_date = dates.get(i).select("span.txt_num").text();

            System.out.println(mv_title + " "  + mv_age + " "  + mv_image);
        }
    }

    /*
        키노라이츠, 셀레니움으로 크롤링
     */
    public static void KinoChart() throws IOException {
        Document doc = Jsoup.connect("https://m.kinolights.com/discover/explore").get();

        Elements element = doc.select("body");

        System.out.println(element);
    }

    /*
        박스오피스 주간 차트
     */
    public static void WeeklyBoxOfficeChart() throws IOException {
        Document doc = Jsoup.connect("https://movie.daum.net/ranking/boxoffice/weekly").get();

        Elements element = doc.select("div.box_boxoffice");

        Elements images = element.select("img.img_thumb");
        Elements ages = element.select("span.txt_tag");
        Elements titles = element.select("a.link_txt");

        for (int i = 0; i < 10; i++) {
            int ranking = (i + 1);
            String mv_image = images.get(i).attr("src");
            String mv_age = ages.get(i).text();
            String mv_title = titles.get(i).text();

            System.out.println(mv_title + " "  + mv_age + " "  + mv_image);
        }
    }

}
