package team2.chartBox.movieApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MvDetailDto {
    private Object movieDetail;
    private Boolean movieScrap;
    private Object curationList;
    private Object reviewBoardList;
    private Object qnaBoardList;

    public MvDetailDto() {
    }
}


