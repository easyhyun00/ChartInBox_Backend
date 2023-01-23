package team2.chartBox.nPartyBoard.dto;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;

@Getter @Setter
public class BoardWriteDto {
    private String postTitle;
    private String postContent;
    private String postUserNickname;
    private String postCategory;
    private String postSpoiler;
    private String postAnonym;

    public BoardWriteDto() {
    }

    public BoardWriteDto(String postTitle, String postContent, String postUserNickname, String postCategory, String postSpoiler, String postAnonym) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postUserNickname = postUserNickname;
        this.postCategory = postCategory;
        this.postSpoiler = postSpoiler;
        this.postAnonym = postAnonym;
    }

    public BoardWriteDto(NPartyBoard nPartyBoard) {
        this.postTitle = nPartyBoard.getPostTitle();
        this.postContent = nPartyBoard.getPostContent();
        this.postUserNickname = nPartyBoard.getPostUserNickname();
        this.postCategory = nPartyBoard.getPostCategory();
        this.postSpoiler = nPartyBoard.getPostSpoiler();
        this.postAnonym = nPartyBoard.getPostAnonym();
    }
}
