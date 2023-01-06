package team2.chartBox.freeBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "freeboard")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class FreeBoard {

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
    private String postLike;

    @Column(name = "POSTREPORT")
    private String postReport;

    // 생성자
    public FreeBoard() {
    }

    public FreeBoard(String postTitle, String postContent, String postUserNickname, String postCategory) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postUserNickname = postUserNickname;
        this.postCategory = postCategory;
    }
}
