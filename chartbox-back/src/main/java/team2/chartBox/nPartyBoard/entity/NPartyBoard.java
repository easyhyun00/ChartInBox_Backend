package team2.chartBox.nPartyBoard.entity;

import lombok.Getter;
import lombok.Setter;
import team2.chartBox.nPartyBoard.dto.BoardWriteDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "npartyboard")
@Entity
@Getter
@Setter
public class NPartyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POSTID")
    private Integer postId;

    @Column(name = "POSTTITLE")
    private String postTitle;

    @Column(name = "POSTCONTENT")
    private String postContent;

    @Column(name = "POSTDATE")
    private LocalDateTime postDate;

    @Column(name = "POSTUSERNICKNAME")
    private String postUserNickname;

    @Column(name = "COUNTVISIT")
    private int countVisit;

    @Column(name = "POSTCATEGORY")
    private String postCategory;

    @Column(name = "POSTLIKE")
    private int postLike;

    @Column(name = "POSTLIKELIST")
    private String postLikeList;

    @Column(name = "POSTCOMMENT")
    private int postComment;

    @Column(name = "POSTSPOILER")
    private String postSpoiler;

    @Column(name = "POSTANONYM")
    private String postAnonym; // 익명 체크

    @Column(name = "POSTREPORT")
    private String postReport; // 게사물 신고

    // 생성자
    public NPartyBoard() {
    }

    public NPartyBoard(Integer postId, String postTitle, String postContent, LocalDateTime postDate, String postUserNickname, int countVisit, String postCategory, int postLike, String postLikeList, int postComment, String postSpoiler, String postAnonym, String postReport) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postDate = postDate;
        this.postUserNickname = postUserNickname;
        this.countVisit = countVisit;
        this.postCategory = postCategory;
        this.postLike = postLike;
        this.postLikeList = postLikeList;
        this.postComment = postComment;
        this.postSpoiler = postSpoiler;
        this.postAnonym = postAnonym;
        this.postReport = postReport;
    }

    public NPartyBoard(BoardWriteDto boardWriteDto) {
        this.postTitle = boardWriteDto.getPostTitle();
        this.postContent = boardWriteDto.getPostContent();
        this.postDate = LocalDateTime.now().plusHours(9L);
        this.postUserNickname = boardWriteDto.getPostUserNickname();
        this.countVisit = 0;
        this.postCategory = boardWriteDto.getPostCategory();
        this.postLike = 0;
        this.postComment = 0;
        this.postSpoiler = boardWriteDto.getPostSpoiler();
        this.postAnonym = boardWriteDto.getPostAnonym();
    }

}
