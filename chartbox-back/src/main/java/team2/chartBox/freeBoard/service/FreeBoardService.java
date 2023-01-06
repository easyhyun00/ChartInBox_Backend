package team2.chartBox.freeBoard.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FreeBoardService {
    @Autowired
    public FreeBoardRepository freeBoardRepository;

    /*
        자유 게시판 전체 목록
     */
    public List<FreeBoard> getFreeBoardList() {
        return freeBoardRepository.findAll();
    }

    /*
        글 보기
     */
    public FreeBoard getFreeBoardDetail(String postId) {
        return freeBoardRepository.findByPostId(Integer.parseInt(postId));
    }

    /*
        자유 게시판
     */
}
