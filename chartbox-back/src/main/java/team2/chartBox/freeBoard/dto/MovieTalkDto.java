package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.freeBoard.entity.FreeBoard;

import java.time.LocalDateTime;

@Getter @Setter
public class MovieTalkDto { // 게시물 목록 상세
    private Integer postId;
    private String postCategory;
    private String postTitle;
    private int postComment;
    private String postUserNickname;
    private String postAnonym; // 익명 체크
    private LocalDateTime postDate;
    private int countVisit;
    private int postLike;

    public MovieTalkDto() {
    }

    public MovieTalkDto(FreeBoard freeBoard) {
        this.postId = freeBoard.getPostId();
        this.postCategory = freeBoard.getPostCategory();
        this.postTitle = freeBoard.getPostTitle();
        this.postComment = freeBoard.getPostComment();
        this.postUserNickname = freeBoard.getPostUserNickname();
        this.postAnonym = freeBoard.getPostAnonym();
        this.postDate = freeBoard.getPostDate();
        this.countVisit = freeBoard.getCountVisit();
        this.postLike = freeBoard.getCountVisit();
    }
}
