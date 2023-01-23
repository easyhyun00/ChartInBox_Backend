package team2.chartBox.nPartyBoard.dto;

import lombok.Getter;
import lombok.Setter;

/*
    게시물 목록 페이지
 */
@Getter
@Setter
public class NPartyBoardListDto {

    private Object boardList;
    private String userNickname;

    public NPartyBoardListDto() {
    }
}
