package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyBoardListDto {
    private Integer postId;
    private String postCategory;
    private String postTitle;
    private int postComment;
    private LocalDateTime postDate;
    private int countVisit;
    private int postLike;
}
