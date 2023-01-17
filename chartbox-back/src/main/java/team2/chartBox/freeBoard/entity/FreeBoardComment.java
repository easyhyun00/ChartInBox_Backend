package team2.chartBox.freeBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import team2.chartBox.freeBoard.dto.CommentDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "freeboardcomment")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class FreeBoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CMTID")
    private Integer cmtId;

    @Column(name = "CMTPOSTID")
    private Integer cmtPostId;

    @Column(name = "CMTCONTENT")
    private String cmtContent;

    @Column(name = "CMTUSERNICKNAME")
    private String cmtUserNickname;

    @Column(name = "CMTREPLY")
    private Integer cmtReply;

    @Column(name = "CMTDATE")
    private LocalDateTime cmtDate;

    public FreeBoardComment() {
    }

    public FreeBoardComment(CommentDto commentDto, String postId) {
        this.cmtPostId = Integer.valueOf(postId);
        this.cmtContent = commentDto.getCmtContent();
        this.cmtUserNickname = commentDto.getCmtUserNickname();
        this.cmtReply = commentDto.getCmtReply();
        this.cmtDate = LocalDateTime.now().plusHours(9L);
    }
}
