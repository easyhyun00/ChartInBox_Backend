package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MyCommentListDto {
    private String boardTitle;
    private Integer cmtPostId;
    private String cmtContent;
    private String cmtDate;
    private Integer cmtId;

    public MyCommentListDto() {
    }
}
