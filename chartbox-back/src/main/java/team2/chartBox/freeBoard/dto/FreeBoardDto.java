package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;

/*
    게시물 상세 보기
 */
@Getter
@Setter
public class FreeBoardDto {
    private Object boardList;
    private String userNickname;

    public FreeBoardDto() {
    }
}
