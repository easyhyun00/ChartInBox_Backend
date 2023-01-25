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
            curationDto.setCurationCatergory(curation.getCurationCategory());

            list.add(curationDto);
        }
        return list;
    }

    /*
        개추운날
     */
    public CurationResponse getCurationCold() {
        CurationResponse curationResponse = new CurationResponse();

        curationResponse.setCurationMovie(getCurationCategory("개추운날"));

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationTitle("이불 밖은 위험해! 한파에 맞서는 영화");
        curationInfo.setCurationText("손이 시려워 꽁, 발이 시려워 꽁! 이불 속에서 전기장판 키고 즐기는 겨울에 어울리는 영화를 모아봤어요.");
        curationInfo.setCurationLink("/curation/cold");

        curationResponse.setCurationInfo(curationInfo);

        return curationResponse;
    }

    /*
        홈데이트
     */
    public CurationResponse getCurationHomeDate() {
        CurationResponse curationResponse = new CurationResponse();

        curationResponse.setCurationMovie(getCurationCategory("홈데이트"));

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationTitle("집에서 데이트 할때 달달하게 보고싶은영화");
        curationInfo.setCurationText("둘이 있는 소중한시간, 영화와 함께 더 달달하게 보내세요:)");
        curationInfo.setCurationLink("/curation/homeDate");

        curationResponse.setCurationInfo(curationInfo);

        return curationResponse;
    }

    /*
        기획자 인생 영화
     */
    public CurationResponse getCurationPick1() {
        CurationResponse curationResponse = new CurationResponse();

        curationResponse.setCurationMovie(getCurationCategory("인생영화1"));

        CurationInfo curationInfo = new CurationInfo();
        curationInfo.setCurationTitle("영화를 사랑하는 기획자들이 손꼽아 뽑은 인생영화");
        curationInfo.setCurationText("기획자들이 차곡차곡 모아놓은 인생영화, 오늘만 특별히 공개할게요!");
        curationInfo.setCurationLink("/curation/pick1");

        curationResponse.setCurationInfo(curationInfo);

        return curationResponse;
    }

}
