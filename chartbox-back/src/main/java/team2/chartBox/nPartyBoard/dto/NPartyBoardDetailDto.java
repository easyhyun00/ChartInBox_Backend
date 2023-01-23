package team2.chartBox.nPartyBoard.dto;

import lombok.Getter;
import lombok.Setter;

/*
    게시물 상세 보기 페이지
 */
@Getter
@Setter
public class NPartyBoardDetailDto {

    private Object postDetail;
    private Object comments;
    private String userNickname;
    private Object postList;

    public NPartyBoardDetailDto() {
    }
}
