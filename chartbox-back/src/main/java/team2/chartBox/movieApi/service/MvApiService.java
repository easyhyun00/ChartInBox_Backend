package team2.chartBox.movieApi.service;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team2.chartBox.movieApi.dto.MvApiDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Service
public class MvApiService {

    @Autowired
    private final RestTemplate restTemplate;

    private final String Mv_api_url =
            "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=XHK4HN18GQY1JVK85RO1";

    public void requestMovie(String mvTitle) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder(Mv_api_url);

        urlBuilder.append("&" + URLEncoder.encode("title","UTF-8") + "=" + URLEncoder.encode(mvTitle, "UTF-8"));

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

            for (int j = 0; j < actorArray.size(); j++) {
                System.out.println(((JSONObject) actorArray.get(j)).get("actorNm"));
            }

            // 줄거리
            JSONObject plots = (JSONObject) tmp.get("plots");
            JSONArray plotArray = (JSONArray) plots.get("plot");
            System.out.println(((JSONObject) plotArray.get(0)).get("plotText"));

            // 감독
            JSONObject directors = (JSONObject) tmp.get("directors");
            JSONArray directorArray = (JSONArray) directors.get("director");
            for (int j = 0; j < directorArray.size(); j++) {
                System.out.println(((JSONObject) directorArray.get(j)).get("directorNm"));
            }

            String title = (String) tmp.get("title");
            String originTitle = title.replace(" !HS ", "").replace(" !HE ", "");
            System.out.println(originTitle);

            System.out.println(tmp.get("genre"));
            System.out.println(tmp.get("runtime"));
            System.out.println(tmp.get("rating"));
            System.out.println(tmp.get("nation"));
            System.out.println(tmp.get("prodYear"));
            System.out.println(tmp.get("titleOrg"));
        }

        rd.close();
        conn.disconnect();
    }

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
            String posters = ((String) tmp.get("posters")).replace("|"," ");

            mvApiDto = new MvApiDto(originTitle, titleEng, mvTitleOrg, plotText, genre, runtime, rating, nation, prodYear, posters, actor, director);
        }

        rd.close();
        conn.disconnect();

        return mvApiDto;
    }
}
