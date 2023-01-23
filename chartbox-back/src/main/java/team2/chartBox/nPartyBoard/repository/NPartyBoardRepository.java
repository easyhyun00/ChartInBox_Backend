package team2.chartBox.nPartyBoard.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;

import java.util.List;

public interface NPartyBoardRepository extends JpaRepository<NPartyBoard, Long> {
    NPartyBoard findByPostId(Integer postId);
    List<NPartyBoard> findAllByPostUserNickname(String postUserNickname);
    List<NPartyBoard> findAllByPostCategory(String postCategory, Sort sort);
}
