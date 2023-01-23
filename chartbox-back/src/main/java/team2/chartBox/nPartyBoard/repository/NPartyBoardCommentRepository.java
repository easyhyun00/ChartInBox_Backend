package team2.chartBox.nPartyBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team2.chartBox.nPartyBoard.entity.NPartyBoardComment;

import java.util.List;

public interface NPartyBoardCommentRepository extends JpaRepository<NPartyBoardComment, Long> {
    List<NPartyBoardComment> findAllByCmtPostId(Integer postId);
    NPartyBoardComment findByCmtId(Integer cmtId);
}
