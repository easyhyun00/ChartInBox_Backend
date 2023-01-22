package team2.chartBox.mainHome.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainHomeDto {
    private Object mvCharts;
    private Object curationList;
    private Object freeBoardList;
    private Object reviewBoardList;
    private Object nPartyBoardList;

    public MainHomeDto() {
    }
}
