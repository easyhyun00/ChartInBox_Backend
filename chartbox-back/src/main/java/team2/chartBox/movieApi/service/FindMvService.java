package team2.chartBox.movieApi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.curation.dto.CurationDto;
import team2.chartBox.curation.dto.CurationInfo;
import team2.chartBox.curation.dto.CurationResponse;
import team2.chartBox.curation.service.CurationService;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;
import team2.chartBox.movieApi.dto.MvApiDto;
import team2.chartBox.movieApi.dto.MvCurationDto;
import team2.chartBox.movieApi.dto.MvScrapDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// @RequiredArgsConstructor
@AllArgsConstructor
@Service
@Slf4j
public class FindMvService {

    @Autowired
    private MvApiService mvApiService;
    private MemberRepository memberRepository;
    private CurationService curationService;

    /*
        제목으로 영화 찾기
        - 검색
     */
    public List<MvScrapDto> findByMvTitle(String mvTitle) throws IOException, ParseException {
        return mvApiService.requestMovie(mvTitle);
    }

    /*
        작품 탐색 필터링
     */
    public List<MvScrapDto> findByMvFilter(String mvGenre, String mvNation, String mvYear) throws IOException, ParseException {
        return mvApiService.requestMovieExplore(mvGenre, mvNation, mvYear);
    }

    /*
        큐레이션
     */
    public List<MvCurationDto> curationList() {
        List<MvCurationDto> list = new ArrayList<>();

        MvCurationDto mvCurationDto1 = new MvCurationDto(); // 큐레이션1

        CurationInfo curationCold = curationService.getCurationCold();
        List<CurationDto> curationCategory1 = curationService.getCurationCategory(curationCold.getCurationCategory());

        mvCurationDto1.setCurationTitle(curationCold.getCurationTitle());
        mvCurationDto1.setCurationUrl(curationCold.getCurationLink());
        mvCurationDto1.setCurationPoster(curationCold.getCurationPoster());

        list.add(mvCurationDto1);

        MvCurationDto mvCurationDto2 = new MvCurationDto(); // 큐레이션2

        CurationInfo curationHomeDate = curationService.getCurationHomeDate();
        List<CurationDto> curationCategory2 = curationService.getCurationCategory(curationHomeDate.getCurationCategory());

        mvCurationDto2.setCurationTitle(curationHomeDate.getCurationTitle());
        mvCurationDto2.setCurationUrl(curationHomeDate.getCurationLink());
        mvCurationDto2.setCurationPoster(curationHomeDate.getCurationPoster());

        list.add(mvCurationDto2);

        MvCurationDto mvCurationDto3 = new MvCurationDto(); // 큐레이션3

        CurationInfo curationPick1 = curationService.getCurationPick1();
        List<CurationDto> curationCategory3 = curationService.getCurationCategory(curationPick1.getCurationCategory());

        mvCurationDto3.setCurationTitle(curationPick1.getCurationTitle());
        mvCurationDto3.setCurationUrl(curationPick1.getCurationLink());
        mvCurationDto3.setCurationPoster(curationPick1.getCurationPoster());

        list.add(mvCurationDto3);

        MvCurationDto mvCurationDto4 = new MvCurationDto(); // 큐레이션4

        CurationInfo curationPick3 = curationService.getCurationPick3();
        List<CurationDto> curationCategory4 = curationService.getCurationCategory(curationPick3.getCurationCategory());

        mvCurationDto4.setCurationTitle(curationPick3.getCurationTitle());
        mvCurationDto4.setCurationUrl(curationPick3.getCurationLink());
        mvCurationDto4.setCurationPoster(curationPick3.getCurationPoster());

        list.add(mvCurationDto4);

        MvCurationDto mvCurationDto5 = new MvCurationDto(); // 큐레이션5

        CurationInfo curationPick2 = curationService.getCurationPick2();
        List<CurationDto> curationCategory5 = curationService.getCurationCategory(curationPick2.getCurationCategory());

        mvCurationDto5.setCurationTitle(curationPick2.getCurationTitle());
        mvCurationDto5.setCurationUrl(curationPick2.getCurationLink());
        mvCurationDto5.setCurationPoster(curationPick2.getCurationPoster());

        list.add(mvCurationDto5);

        MvCurationDto mvCurationDto6 = new MvCurationDto(); // 큐레이션6

        CurationInfo curationSad = curationService.getCurationSad();
        List<CurationDto> curationCategory6 = curationService.getCurationCategory(curationSad.getCurationCategory());

        mvCurationDto6.setCurationTitle(curationSad.getCurationTitle());
        mvCurationDto6.setCurationUrl(curationSad.getCurationLink());
        mvCurationDto6.setCurationPoster(curationSad.getCurationPoster());

        list.add(mvCurationDto6);

        return list;
    }

    /*
        id로 영화 찾기
        - 영화 상세 보기
     */
    public MvApiDto findByMvId(String mvId) throws IOException, ParseException {
        return mvApiService.requestMovieById(mvId);
    }

    /*
        id로 영화 찾기
        - 스크랩 목록
     */
    public MvScrapDto findByScrap(String mvId) throws IOException, ParseException {
        MvApiDto mvApiDto = mvApiService.requestMovieById(mvId);
        MvScrapDto mvScrapDto = new MvScrapDto();
        mvScrapDto.setMvId(mvId);
        mvScrapDto.setMvTitle(mvApiDto.getMvTitle());
        mvScrapDto.setMvPoster(mvApiDto.getMvPoster());
        return mvScrapDto;
    }

    /*
        영화 스크랩 하기
     */
    public String movieClipping(String userNickname, String userMovieScrap) throws ParseException {
        log.info(userMovieScrap);
        Member member = memberRepository.findByUserNickname(userNickname);
        if(member.getUserMovieScrap()==null) { // 새로 추가
            JSONArray mvClipList = new JSONArray();
            mvClipList.add(userMovieScrap);
            member.setUserMovieScrap(mvClipList.toString());
            memberRepository.save(member);
            log.info(mvClipList.toString());
            return "add";
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
                return "cancel";
            } else { // 스크랩하지 않은 영화
                jsonArray.add(userMovieScrap);
                member.setUserMovieScrap(jsonArray.toString());
                memberRepository.save(member);
                log.info("스크랩 영화 추가");
                log.info(jsonArray.toString());
                return "add";
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
