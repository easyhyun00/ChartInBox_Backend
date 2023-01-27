package team2.chartBox.curation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.curation.dto.CurationDto;
import team2.chartBox.curation.dto.CurationInfo;
import team2.chartBox.curation.dto.CurationResponse;
import team2.chartBox.curation.entity.Curation;
import team2.chartBox.curation.repository.CurationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CurationService {

    @Autowired
    public CurationRepository curationRepository;

    /*
        큐테이션 카테고리 별 영화 데이터
     */
    public List<CurationDto> getCurationCategory(String category) {

        List<CurationDto> list = new ArrayList<>();
        List<Curation> curationList = curationRepository.findAllByCurationCategory(category);
        for (int i = 0; i < curationList.size(); i++) {
            CurationDto curationDto = new CurationDto();
            Curation curation = curationList.get(i);
            curationDto.setMovieId(curation.getMovieId());
            curationDto.setMovieTitle(curation.getMovieTitle());
            curationDto.setMoviePoster(curation.getMoviePoster());
            curationDto.setMovieAge(curation.getMovieAge());

            list.add(curationDto);
        }
        return list;
    }

    /*
        개추운날
     */
    public CurationInfo getCurationCold() {

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationPoster("https://photo.coolenjoy.co.kr/data/editor/1912/Bimg_02a61af7c4323a0d0810ff8ee33e580f_juhl.jpg");
        curationInfo.setCurationTitle("이불 밖은 위험해! 한파에 맞서는 영화");
        curationInfo.setCurationText("손이 시려워 꽁, 발이 시려워 꽁! 이불 속에서 전기장판 키고 즐기는 겨울에 어울리는 영화를 모아봤어요.");
        curationInfo.setCurationLink("/curation/cold");
        curationInfo.setCurationCategory("개추운날");

        return curationInfo;
    }

    /*
        홈데이트
     */
    public CurationInfo getCurationHomeDate() {

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationPoster("https://p4.wallpaperbetter.com/wallpaper/957/116/902/romantic-couple-love-hands-wallpaper-preview.jpg");
        curationInfo.setCurationTitle("집에서 데이트 할때 달달하게 보고싶은영화");
        curationInfo.setCurationText("둘이 있는 소중한시간, 영화와 함께 더 달달하게 보내세요:)");
        curationInfo.setCurationLink("/curation/homeDate");
        curationInfo.setCurationCategory("홈데이트");

        return curationInfo;
    }

    /*
        기획자 인생 영화
     */
    public CurationInfo getCurationPick1() {

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationPoster("https://p4.wallpaperbetter.com/wallpaper/779/691/639/movies-film-reel-technology-projector-8mm-wallpaper-preview.jpg");
        curationInfo.setCurationTitle("영화를 사랑하는 기획자들이 손꼽아 뽑은 인생영화");
        curationInfo.setCurationText("기획자들이 차곡차곡 모아놓은 인생영화, 오늘만 특별히 공개할게요!");
        curationInfo.setCurationLink("/curation/pick1");
        curationInfo.setCurationCategory("인생영화1");

        return curationInfo;
    }

    /*
        기획자 인생 영화
    */
    public CurationInfo getCurationPick2() {

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationPoster("https://p4.wallpaperbetter.com/wallpaper/22/762/507/film-movie-filmmaker-movie-director-wallpaper-preview.jpg");
        curationInfo.setCurationTitle("[앵콜!!!] 영화를 사랑하는 기획자들이 손꼽아 뽑은 인생영화 2");
        curationInfo.setCurationText("기획자들이 차곡차곡 모아놓은 인생영화, 오늘만 특별히 공개할게요!");
        curationInfo.setCurationLink("/curation/pick2");
        curationInfo.setCurationCategory("인생영화2");

        return curationInfo;
    }

    /*
        개발자 인생 영화
     */
    public CurationInfo getCurationPick3() {

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationPoster("https://p4.wallpaperbetter.com/wallpaper/812/525/134/code-coder-coding-computer-wallpaper-preview.jpg");
        curationInfo.setCurationTitle("자꾸 보고 싶은 개발자들의 소중한 인생영화");
        curationInfo.setCurationText("유쾌하지만 조금은 아련한, 개발자들의 소중한 인생영화 한번 보실래요?");
        curationInfo.setCurationLink("/curation/pick3");
        curationInfo.setCurationCategory("인생영화3");

        return curationInfo;
    }

    /*
        우울한날
     */
    public CurationInfo getCurationSad() {

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationPoster("https://p4.wallpaperbetter.com/wallpaper/194/313/111/dark-depression-mood-people-wallpaper-preview.jpg");
        curationInfo.setCurationTitle("우울한 날, 나를 위로해주는 영화");
        curationInfo.setCurationText("때때로 울고싶거나 누군가에게 위로받고 싶을 때, 나를 따듯하게 안아주는 영화");
        curationInfo.setCurationLink("/curation/sad");
        curationInfo.setCurationCategory("우울한날");

        return curationInfo;
    }

}
