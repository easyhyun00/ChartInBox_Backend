package team2.chartBox.nPartyBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;

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

    public PostDetailDto(NPartyBoard nPartyBoard) {
        this.postId = nPartyBoard.getPostId();
        this.postTitle = nPartyBoard.getPostTitle();
        this.postContent = nPartyBoard.getPostContent();
        this.postDate = nPartyBoard.getPostDate().toString();
        this.postUserNickname = nPartyBoard.getPostUserNickname();
        this.countVisit = nPartyBoard.getCountVisit();
        this.postCategory = nPartyBoard.getPostCategory();
        this.postLike = nPartyBoard.getPostLike();
        this.postLikeList = nPartyBoard.getPostLikeList();
        this.postComment = nPartyBoard.getPostComment();
        this.postSpoiler = nPartyBoard.getPostSpoiler();
        this.postAnonym = nPartyBoard.getPostAnonym();
        this.postReport = nPartyBoard.getPostReport();
    }
}
