package team2.chartBox.nPartyBoard.dto;

import lombok.Getter;
import lombok.Setter;

/*
    댓글
 */
@Getter
@Setter
public class CommentDto {

    private String cmtContent;
    private String cmtUserNickname;
    private Integer cmtReply;

    public CommentDto() {
    }
}
