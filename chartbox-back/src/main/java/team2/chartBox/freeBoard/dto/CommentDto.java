package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String cmtContent;
    private String cmtUserNickname;
    private Integer cmtReply;

    public CommentDto() {
    }
}
