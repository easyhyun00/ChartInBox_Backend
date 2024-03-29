package team2.chartBox.freeBoard.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team2.chartBox.freeBoard.entity.FreeBoardComment;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {
    List<FreeBoardComment> findAllByCmtPostId(Integer postId);
    FreeBoardComment findByCmtId(Integer cmtId);
    List<FreeBoardComment> findAllByCmtUserNickname(String cmtUserNickname);
    List<FreeBoardComment> findAllByCmtUserNickname(String cmtUserNickname, Sort sort);

}
