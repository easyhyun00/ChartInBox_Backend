package team2.chartBox.nPartyBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;

import java.time.LocalDateTime;

/*
    게시물 목록 상세
 */
@Getter
@Setter
public class NPartyBoardListDetailDto {
    private Integer postId;
    private String postCategory;
    private String postTitle;
    private int postComment;
    private String postUserNickname;
    private String postAnonym; // 익명 체크
    private String postDate;
    private int countVisit;
    private int postLike;

    public NPartyBoardListDetailDto() {
    }

    public NPartyBoardListDetailDto(NPartyBoard nPartyBoard) {
        this.postId = nPartyBoard.getPostId();
        this.postCategory = nPartyBoard.getPostCategory();
        this.postTitle = nPartyBoard.getPostTitle();
        this.postComment = nPartyBoard.getPostComment();
        this.postUserNickname = nPartyBoard.getPostUserNickname();
        this.postAnonym = nPartyBoard.getPostAnonym();
        this.postDate = nPartyBoard.getPostDate().toString();
        this.countVisit = nPartyBoard.getCountVisit();
        this.postLike = nPartyBoard.getCountVisit();
    }
}
