package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.freeBoard.entity.FreeBoard;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyBoardListDto {
    private Integer postId;
    private String postCategory;
    private String postTitle;
    private int postComment;
    private String postUserNickname;
    private String postDate;
    private int countVisit;
    private int postLike;

    public MyBoardListDto(FreeBoard freeBoard) {
        this.postId = freeBoard.getPostId();
        this.postCategory = freeBoard.getPostCategory();
        this.postTitle = freeBoard.getPostTitle();
        this.postComment = freeBoard.getPostComment();
        this.postUserNickname = freeBoard.getPostUserNickname();
        this.postDate = freeBoard.getPostDate().toString();
        this.countVisit = freeBoard.getCountVisit();
        this.postLike = freeBoard.getPostLike();
    }
}
