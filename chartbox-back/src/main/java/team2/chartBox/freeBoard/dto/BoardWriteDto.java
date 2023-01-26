package team2.chartBox.freeBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.freeBoard.entity.FreeBoard;

/*
    게시물 작성
 */
@Getter @Setter
public class BoardWriteDto {
    private String postTitle;
    private String postContent;
    private String postUserNickname;
    private String postCategory;
    private String postSpoiler;
    private String postAnonym;
    private String movieId;

    public BoardWriteDto() {
    }

    public BoardWriteDto(String postTitle, String postContent, String postUserNickname, String postCategory, String postSpoiler, String postAnonym, String movieId) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postUserNickname = postUserNickname;
        this.postCategory = postCategory;
        this.postSpoiler = postSpoiler;
        this.postAnonym = postAnonym;
        this.movieId = movieId;
    }

    public BoardWriteDto(FreeBoard freeBoard) {
        this.postTitle = freeBoard.getPostTitle();
        this.postContent = freeBoard.getPostContent();
        this.postUserNickname = freeBoard.getPostUserNickname();
        this.postCategory = freeBoard.getPostCategory();
        this.postSpoiler = freeBoard.getPostSpoiler();
        this.postAnonym = freeBoard.getPostAnonym();
        this.movieId = freeBoard.getMovieId();
    }
}
