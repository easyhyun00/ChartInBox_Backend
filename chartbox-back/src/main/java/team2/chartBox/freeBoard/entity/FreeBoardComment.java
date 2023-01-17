package team2.chartBox.freeBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
