package team2.chartBox.freeBoard.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team2.chartBox.freeBoard.dto.MovieTalkDto;
import team2.chartBox.freeBoard.entity.FreeBoard;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {
    FreeBoard findByPostId(Integer postId);
    List<FreeBoard> findAllByPostUserNickname(String postUserNickname, Sort sort);
    List<FreeBoard> findAllByPostCategory(String postCategory, Sort sort);
}
