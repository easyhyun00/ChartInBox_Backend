package team2.chartBox.movieApi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team2.chartBox.movieApi.dto.MvApiDto;
import team2.chartBox.movieApi.dto.MvScrapDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class MvApiService {

    @Autowired
    private final RestTemplate restTemplate;

    private final String Mv_api_url =
            "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=XHK4HN18GQY1JVK85RO1&listCount=200";

    /*
        제목으로 영화 검색
     */
    public List<MvScrapDto> requestMovie(String mvTitle) throws IOException, ParseException {

        List<MvScrapDto> list = new ArrayList<>();

        StringBuilder urlBuilder = new StringBuilder(Mv_api_url);

        urlBuilder.append("&" + URLEncoder.encode("title","UTF-8") + "=" + URLEncoder.encode(mvTitle, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        JSONObject result = null;

        result  = (JSONObject) new JSONParser().parse(sb.toString());

        JSONObject data = (JSONObject) ((JSONArray) result.get("Data")).get(0);

        JSONArray array = (JSONArray) data.get("Result");

        JSONObject tmp;

        if (array == null)
            return list;

        for (int i = 0; i < array.size(); i++) {
            MvScrapDto mvListDto = new MvScrapDto();

            tmp = (JSONObject) array.get(i);

            String genre = (String) tmp.get("genre"); // 장르 필터링
            if (genre.equals("에로") || genre.equals(""))
                continue;

            String title = (String) tmp.get("title"); // 제목
            String originTitle = title.replace(" !HS ", "").replace(" !HE ", "");
            mvListDto.setMvTitle(originTitle);

            String posters = ((String) tmp.get("posters")); // 포스터
            if (posters.equals(""))
                continue;
            String poster = posters.substring(posters.lastIndexOf("|")+1);
            mvListDto.setMvPoster(poster);

            String id = (String) tmp.get("DOCID");// 아이디
            mvListDto.setMvId(id);

            list.add(mvListDto);
        }

        rd.close();
        conn.disconnect();

        return list;
    }

    /*
        영화 작품 탐색
        // 장르
        // 개봉년도
        // 국가

     */
    public List<MvScrapDto> requestMovieExplore(String mvGenre, String mvNation, String mvYear) throws IOException, ParseException {

        List<MvScrapDto> list = new ArrayList<>();

        StringBuilder urlBuilder = new StringBuilder(Mv_api_url);

        if (mvGenre != null) {
            urlBuilder.append("&" + URLEncoder.encode("genre","UTF-8") + "=" + URLEncoder.encode(mvGenre, "UTF-8"));
        }
        if (mvNation != null) {
            urlBuilder.append("&" + URLEncoder.encode("nation","UTF-8") + "=" + URLEncoder.encode(mvNation, "UTF-8"));
        }
        if (mvYear != null) {
            String lastYear = String.valueOf((Integer.parseInt(mvYear) + 10));
            urlBuilder.append("&" + URLEncoder.encode("releaseDts","UTF-8") + "=" + URLEncoder.encode(mvYear, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("releaseDte","UTF-8") + "=" + URLEncoder.encode(lastYear, "UTF-8"));
        }
        if (mvGenre == null && mvNation == null && mvYear == null) { // 필터링 없을 때, 계속 랜덤 값
            int min = 0;
            int max = 493;
            String random = String.valueOf((int) ((Math.random() * (max - min)) + min));
            log.info("랜덤 수 {}",random);
            urlBuilder.append("&" + URLEncoder.encode("startCount","UTF-8") + "=" + URLEncoder.encode(random, "UTF-8"));
        }

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        JSONObject result = null;

        result  = (JSONObject) new JSONParser().parse(sb.toString());

        JSONObject data = (JSONObject) ((JSONArray) result.get("Data")).get(0);

        JSONArray array = (JSONArray) data.get("Result");

        JSONObject tmp;

        if (array == null)
            return list;

        for (int i = 0; i < array.size(); i++) {
            MvScrapDto mvListDto = new MvScrapDto();

            tmp = (JSONObject) array.get(i);

            String genre = (String) tmp.get("genre"); // 장르 필터링
            if (genre.equals("에로") || genre.equals("") || genre.contains("에로"))
                continue;

            String title = (String) tmp.get("title"); // 제목
            String originTitle = (title.replace(" !HS ", "").replace(" !HE ", "")).substring(1);
            mvListDto.setMvTitle(originTitle);

            String posters = ((String) tmp.get("posters")); // 포스터
            if (posters.equals(""))
                continue;
            String poster = posters.substring(posters.lastIndexOf("|")+1);
            mvListDto.setMvPoster(poster);

            String id = (String) tmp.get("DOCID");// 아이디
            mvListDto.setMvId(id);

            String prodYear = (String) tmp.get("prodYear");
            log.info("연도 {}",prodYear);

            list.add(mvListDto);

            if (list.size() >= 15)
                return list;
        }

        rd.close();
        conn.disconnect();

        return list;
    }

    /*
        영화 상세보기 페이지
     */
    public MvApiDto requestMovieById(String mvId) throws IOException, ParseException {

        MvApiDto mvApiDto = null;

        StringBuilder urlBuilder = new StringBuilder(Mv_api_url);

        String movieId = mvId.substring(0, 1);
        String movieSeq = mvId.substring(1);

        urlBuilder.append("&" + URLEncoder.encode("movieId","UTF-8") + "=" + URLEncoder.encode(movieId, "UTF-8"))
                  .append("&" + URLEncoder.encode("movieSeq","UTF-8") + "=" + URLEncoder.encode(movieSeq, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        JSONObject result = null;

        result  = (JSONObject) new JSONParser().parse(sb.toString());

        JSONObject data = (JSONObject) ((JSONArray) result.get("Data")).get(0);

        JSONArray array = (JSONArray) data.get("Result");

        JSONObject tmp;
        for (int i = 0; i < array.size(); i++) {
            tmp = (JSONObject) array.get(i);

            // 배우 목록
            JSONObject actors = (JSONObject) tmp.get("actors");
            JSONArray actorArray = (JSONArray) actors.get("actor");

            String[] actor = new String[actorArray.size()];
            for (int j = 0; j < actorArray.size(); j++) {
                actor[j] = (String) ((JSONObject) actorArray.get(j)).get("actorNm");
            }

            // 줄거리
            JSONObject plots = (JSONObject) tmp.get("plots");
            JSONArray plotArray = (JSONArray) plots.get("plot");
            String plotText = (String) ((JSONObject) plotArray.get(0)).get("plotText");

            // 감독
            JSONObject directors = (JSONObject) tmp.get("directors");
            JSONArray directorArray = (JSONArray) directors.get("director");

            String[] director = new String[directorArray.size()];
            for (int j = 0; j < directorArray.size(); j++) {
                director[j] = (String) ((JSONObject) directorArray.get(j)).get("directorNm");
            }

            String title = (String) tmp.get("title");
            String originTitle = (title.replace(" !HS ", "").replace(" !HE ", "")).substring(1);

            String titleEng = (String) tmp.get("titleEng");
            String mvTitleOrg = (String) tmp.get("mvTitleOrg");
            String genre = (String) tmp.get("genre");
            String runtime = (String) tmp.get("runtime");
            String rating = (String) tmp.get("rating");
            String nation = (String) tmp.get("nation");
            String prodYear = (String) tmp.get("prodYear");
            String posters = ((String) tmp.get("posters"));
            int idx = posters.indexOf("|");
            String poster = posters.substring(0, idx);

            mvApiDto = new MvApiDto(originTitle, titleEng, mvTitleOrg, plotText, genre, runtime, rating, nation, prodYear, poster, actor, director);
        }

        rd.close();
        conn.disconnect();

        return mvApiDto;
    }
}
