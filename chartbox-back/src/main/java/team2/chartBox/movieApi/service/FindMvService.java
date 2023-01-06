package team2.chartBox.movieApi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;
import team2.chartBox.movieApi.dto.MvApiDto;

import java.io.IOException;

// @RequiredArgsConstructor
@AllArgsConstructor
@Service
@Slf4j
public class FindMvService {

    @Autowired
    private MvApiService mvApiService;
    private MemberRepository memberRepository;

    /*
        제목으로 영화 찾기
        - 검색
     */
    public void findByMvTitle(String mvTitle) throws IOException, ParseException {
        mvApiService.requestMovie(mvTitle);
    }

    /*
        id로 영화 찾기
        - 영화 상세 보기
     */
    public MvApiDto findByMvId(String mvId) throws IOException, ParseException {
        return mvApiService.requestMovieById(mvId);
    }

    /*
        영화 스크랩 하기
     */
    public boolean movieClipping(String userNickname, String userMovieScrap) throws ParseException {
        log.info(userMovieScrap);
        Member member = memberRepository.findByUserNickname(userNickname);
        if(member.getUserMovieScrap()==null) { // 새로 추가
            JSONArray mvClipList = new JSONArray();
            mvClipList.add(userMovieScrap);
            member.setUserMovieScrap(mvClipList.toString());
            memberRepository.save(member);
            log.info(mvClipList.toString());
            return true;
        } else { // 수정해서 추가
            JSONParser jsonParser = new JSONParser();
            String movieScrapList = member.getUserMovieScrap();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(movieScrapList);
            if (jsonArray.contains(userMovieScrap)) {
                // 이미 스크랩한 영화
                jsonArray.remove(userMovieScrap);
                member.setUserMovieScrap(jsonArray.toString());
                memberRepository.save(member);
                log.info("스크랩 취소");
                log.info(jsonArray.toString());
                return false;
            } else { // 스크랩하지 않은 영화
                jsonArray.add(userMovieScrap);
                member.setUserMovieScrap(jsonArray.toString());
                memberRepository.save(member);
                log.info("스크랩 영화 추가");
                log.info(jsonArray.toString());
                return true;
            }

        }
    }

    /*
        해당 멤버가 스크랩 했는지 안했는지
     */
    public boolean movieClipState(String userNickname, String movieId) throws ParseException {
        Member findMember = memberRepository.findByUserNickname(userNickname);
        String userMovieScrap = findMember.getUserMovieScrap();
        if (userMovieScrap != null) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(userMovieScrap);
            if (jsonArray.contains(movieId)) {
                return true; // 스크랩 함
            } else {
                return false; // 스크랩 안 함
            }
        } else {
            return false; // 스크랩 안 함
        }
    }
}
