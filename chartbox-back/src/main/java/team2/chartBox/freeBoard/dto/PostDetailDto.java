package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.freeBoard.entity.FreeBoard;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter @Setter
public class PostDetailDto {
    private Integer postId;
    private String postTitle;
    private String postContent;
    private String postDate;
    private String postUserNickname;
    private int countVisit;
    private String postCategory;
    private int postLike;
    private String postLikeList;
    private int postComment;
    private String postSpoiler;
    private String postAnonym; // 익명 체크
    private String postReport; // 게사물 신고
    private String movieId;

    public PostDetailDto(FreeBoard freeBoard) {
        this.postId = freeBoard.getPostId();
        this.postTitle = freeBoard.getPostTitle();
        this.postContent = freeBoard.getPostContent();
        this.postDate = freeBoard.getPostDate().toString();
        this.postUserNickname = freeBoard.getPostUserNickname();
        this.countVisit = freeBoard.getCountVisit();
        this.postCategory = freeBoard.getPostCategory();
        this.postLike = freeBoard.getPostLike();
        this.postLikeList = freeBoard.getPostLikeList();
        this.postComment = freeBoard.getPostComment();
        this.postSpoiler = freeBoard.getPostSpoiler();
        this.postAnonym = freeBoard.getPostAnonym();
        this.postReport = freeBoard.getPostReport();
        this.movieId = freeBoard.getMovieId();
    }
}
