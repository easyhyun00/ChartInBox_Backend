package team2.chartBox.freeBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import team2.chartBox.freeBoard.dto.BoardWriteDto;
import team2.chartBox.freeBoard.service.FreeBoardService;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "freeboard")
@Entity
@Getter
@Setter
// @AllArgsConstructor
public class FreeBoard {

    @Autowired
    private FreeBoardService freeBoardService;

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

    @Column(name = "POSTCOMMENT")
    private int postComment;

    @Column(name = "POSTSPOILER")
    private String postSpoiler;

    @Column(name = "POSTANONYM")
    private String postAnonym; // 익명 체크

    // 생성자
    public FreeBoard() {
    }

    public FreeBoard(Integer postId, String postTitle, String postContent, LocalDateTime postDate, String postUserNickname, int countVisit, String postCategory, int postLike, int postComment, String postSpoiler, String postAnonym) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postDate = LocalDateTime.now().plusHours(9L);
        this.postUserNickname = postUserNickname;
        this.countVisit = countVisit;
        this.postCategory = postCategory;
        this.postLike = postLike;
        this.postComment = freeBoardService.getCommentCnt(postId);
        this.postSpoiler = postSpoiler;
        this.postAnonym = postAnonym;
    }

    public FreeBoard(BoardWriteDto boardWriteDto) {
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
