package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;

/*
    게시판 목록 보기
 */
@Getter
@Setter
public class FreeBoardDetailDto {
    private Object postDetail;
    private Object comments;
    private String userNickname;

    public FreeBoardDetailDto() {
    }
}
