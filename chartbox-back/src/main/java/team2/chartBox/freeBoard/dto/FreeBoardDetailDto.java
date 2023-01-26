package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;

/*
    게시물 상세 보기
 */
@Getter
@Setter
public class FreeBoardDetailDto {
    private Object postDetail;
    private Object movieInfo;
    private Object comments;
    private String userNickname;
    private Object postList;

    public FreeBoardDetailDto() {
    }
}
