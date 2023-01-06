package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoardDetailDto {
    private Object freeBoard;
    private Object comments;
    private String userNickname;

    public FreeBoardDetailDto() {
    }
}
