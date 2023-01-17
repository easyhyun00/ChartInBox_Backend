package team2.chartBox.freeBoard.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.dto.BoardWriteDto;
import team2.chartBox.freeBoard.dto.CommentDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.entity.FreeBoardComment;
import team2.chartBox.freeBoard.repository.FreeBoardCommentRepository;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.member.entity.Member;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class FreeBoardService {

    @Autowired
    public FreeBoardRepository freeBoardRepository;
    public FreeBoardCommentRepository freeBoardCommentRepository;

    /*
        무비토크 - 전체 글 목록
     */
    public List<FreeBoard> getMovieTalkList() {
        return freeBoardRepository.findAll();
    }

    /*
        무비토크 - 자유게시판 목록
     */
    public List<FreeBoard> getFreeBoardList() {
        return freeBoardRepository.findAllByPostCategory("자유");
    }

    /*
        무비토크 - 리뷰게시판 목록
     */
    public List<FreeBoard> getReviewBoardList() {
        return freeBoardRepository.findAllByPostCategory("리뷰");
    }

    /*
        무비토크 - 리뷰게시판 목록
     */
    public List<FreeBoard> getQnaBoardList() {
        return freeBoardRepository.findAllByPostCategory("Q&A");
    }

    /*
        글 보기
     */
    public FreeBoard getFreeBoardDetail(String postId) {
        return freeBoardRepository.findByPostId(Integer.parseInt(postId));
    }

    /*
        댓글 보기
     */
    public List<FreeBoardComment> getCommentList(String postId) {
        return freeBoardCommentRepository.findAllByCmtPostId(Integer.parseInt(postId));
    }

    /*
        게시물 수정 - 페이지
     */
    public String EditBoard(String postId, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoard findBoard = getFreeBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (member.getUserNickname().equals(findBoard.getPostUserNickname())) {
            return "success"; //
        }
        return "member"; // 잘못된 유저
    }

    /*
        게시물 수정 - 제출
     */
    public String EditBoardSubmit(String postId, BoardWriteDto boardWriteDto, Member member) {

        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoard findBoard = getFreeBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }

        if (findBoard.getPostUserNickname().equals(member.getUserNickname())) {

            // 게시물 수정
            findBoard.setPostTitle(boardWriteDto.getPostTitle());
            findBoard.setPostContent(boardWriteDto.getPostContent());
            findBoard.setPostCategory(boardWriteDto.getPostCategory());
            findBoard.setPostSpoiler(boardWriteDto.getPostSpoiler());
            findBoard.setPostAnonym(boardWriteDto.getPostAnonym());

            freeBoardRepository.save(findBoard);

            log.info("게시물 수정");

            return "success";
        }

        return "member"; // 잘못된 유저
    }

    /*
        게시물 삭제 - 제출
     */
    public String DeleteBoardSubmit(String postId, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoard findBoard = getFreeBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (findBoard.getPostUserNickname().equals(member.getUserNickname())) {
            freeBoardRepository.delete(findBoard);
            return "success"; // 성공
        }
        return "member"; // 잘못된 사용자
    }

    /*
      댓글 - 작성
   */
    public String CommentAdd(String postId, CommentDto commentDto, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoard findBoard = getFreeBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (commentDto.getCmtUserNickname().equals(member.getUserNickname())) {
            FreeBoardComment freeBoardComment = new FreeBoardComment(commentDto,postId);
            freeBoardCommentRepository.save(freeBoardComment);

            findBoard.setPostComment(findBoard.getPostComment()+1);
            freeBoardRepository.save(findBoard);

            return "success";
        }
        return "member"; // 잘못된 사용자
    }

    /*
        댓글 - 수정
     */
    public String CommentEdit(String postId, String cmtId, CommentDto commentDto, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoard findBoard = getFreeBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (cmtId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoardComment findComment = freeBoardCommentRepository.findByCmtId(Integer.valueOf(cmtId));
        if (findComment == null) {
            return "comment"; // 없는 댓글
        }
        if (findComment.getCmtUserNickname().equals(member.getUserNickname())) {

            // 댓글 수정
            findComment.setCmtContent(commentDto.getCmtContent());

            freeBoardCommentRepository.save(findComment);

            return "success";
        }
        return "member"; // 잘못된 사용자
    }

    /*
        댓글 - 삭제
     */
    public String CommentDelete(String postId, String cmtId, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoard findBoard = getFreeBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (cmtId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        FreeBoardComment findComment = freeBoardCommentRepository.findByCmtId(Integer.valueOf(cmtId));
        if (findComment == null) {
            return "comment"; // 없는 댓글
        }
        if (findComment.getCmtUserNickname().equals(member.getUserNickname())) {
            // 댓글 삭제
            freeBoardCommentRepository.delete(findComment);

            findBoard.setPostComment(findBoard.getPostComment()-1);
            freeBoardRepository.save(findBoard);

            return "success";
        }
        return "member"; // 잘못된 사용자
    }

    /*
        조회수
     */
    public void PostViewCnt(FreeBoard freeBoardDetail) {
        freeBoardDetail.setCountVisit(freeBoardDetail.getCountVisit()+1);
        freeBoardRepository.save(freeBoardDetail);
    }
}
