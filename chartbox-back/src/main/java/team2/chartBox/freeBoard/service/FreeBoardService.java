package team2.chartBox.freeBoard.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.dto.BoardWriteDto;
import team2.chartBox.freeBoard.dto.CommentDto;
import team2.chartBox.freeBoard.dto.MovieTalkDto;
import team2.chartBox.freeBoard.dto.PostDetailDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.entity.FreeBoardComment;
import team2.chartBox.freeBoard.repository.FreeBoardCommentRepository;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FreeBoardService {

    @Autowired
    public FreeBoardRepository freeBoardRepository;
    public FreeBoardCommentRepository freeBoardCommentRepository;
    public ModelMapper mapper;

    /*
        무비토크 - 전체 글 목록
     */
    public List<MovieTalkDto> getMovieTalkList() {
        List<FreeBoard> boardList = freeBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "postId"));

        List<MovieTalkDto> returnList = boardList.stream()
                .map(post -> mapper.map(post,MovieTalkDto.class))
                .collect(Collectors.toList());

        return returnList;
    }

    /*
        무비토크 - 자유게시판 목록
     */
    public List<MovieTalkDto> getFreeBoardList() {
        List<FreeBoard> boardList = freeBoardRepository.findAllByPostCategory("자유",Sort.by(Sort.Direction.DESC, "postId"));

        List<MovieTalkDto> returnList = boardList.stream()
                .map(post -> mapper.map(post,MovieTalkDto.class))
                .collect(Collectors.toList());

        return returnList;
    }

    /*
        무비토크 - 리뷰게시판 목록
     */
    public List<MovieTalkDto> getReviewBoardList() {
        List<FreeBoard> boardList = freeBoardRepository.findAllByPostCategory("리뷰",Sort.by(Sort.Direction.DESC, "postId"));

        List<MovieTalkDto> returnList = boardList.stream()
                .map(post -> mapper.map(post,MovieTalkDto.class))
                .collect(Collectors.toList());

        return returnList;
    }

    /*
        무비토크 - Q&A 게시판 목록
     */
    public List<MovieTalkDto> getQnaBoardList() {
        List<FreeBoard> boardList = freeBoardRepository.findAllByPostCategory("Q&A",Sort.by(Sort.Direction.DESC, "postId"));

        List<MovieTalkDto> returnList = boardList.stream()
                .map(post -> mapper.map(post,MovieTalkDto.class))
                .collect(Collectors.toList());

        return returnList;
    }

    /*
        글 상세 보기
     */
    public FreeBoard getFreeBoardDetail(String postId) {
        return freeBoardRepository.findByPostId(Integer.parseInt(postId));
    }

    /*
        글 상세 보기
        dto
     */
    public PostDetailDto getFreeBoardDetail2(String postId) {

        FreeBoard freeboard = freeBoardRepository.findByPostId(Integer.parseInt(postId));

        PostDetailDto postDetailDto = new PostDetailDto(freeboard);

        return postDetailDto;
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

    /*
        신고
     */
    public String PostReport(String postId, Member member) throws ParseException {

        if (member == null) {
            return "non-member"; // 비회원 신고
        }
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }

        FreeBoard findBoard = getFreeBoardDetail(postId);

        if (findBoard == null) {
            return "board"; // 없는 게시물
        }

        String postReport = findBoard.getPostReport();

        if (postReport == null) { // 신고한 회원 새로 추가
            JSONArray reportMember = new JSONArray();
            reportMember.add(member.getUserNickname());
            findBoard.setPostReport(reportMember.toString());
            freeBoardRepository.save(findBoard);
            log.info(reportMember.toString());
        } else { // 신고한 회원 추가
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(postReport);

            if (jsonArray.contains(member.getUserNickname())) { // 이미 신고
                log.info("이미 신고한 회원");
                return "overlap";
            }

            jsonArray.add(member.getUserNickname());

            if (jsonArray.size() == 5) { // 게시물 삭제
                freeBoardRepository.delete(findBoard);
                log.info("게시물 삭제");
                return "delete";

            } else {
                findBoard.setPostReport(jsonArray.toString());
                freeBoardRepository.save(findBoard);
                log.info(jsonArray.toString());
            }

        }
        return "success";
    }

    /*
        좋아요
     */
    public String PostLike(String postId,Member member) throws ParseException {

        if (member == null) {
            return "non-member"; // 비회원 신고
        }
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }

        FreeBoard findBoard = getFreeBoardDetail(postId);

        if (findBoard == null) {
            return "board"; // 없는 게시물
        }

        String likeList = findBoard.getPostLikeList();

        if (likeList == null) { // 좋아요 회원 새로 추가
            JSONArray likeMemberList = new JSONArray();
            likeMemberList.add(member.getUserNickname());
            findBoard.setPostLikeList(likeMemberList.toString());
            findBoard.setPostLike(findBoard.getPostLike()+1);
            freeBoardRepository.save(findBoard);
            log.info(likeMemberList.toString());
        } else { // 이미 좋아요 회원 있는 경우
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(likeList);

            if (jsonArray.contains(member.getUserNickname())) { // 이미 좋아요 누름
                jsonArray.remove(member.getUserNickname());
                findBoard.setPostLikeList(jsonArray.toString());
                findBoard.setPostLike(findBoard.getPostLike()-1);
                freeBoardRepository.save(findBoard);
                log.info("좋아요 취소");
                return "cancel";
            } else { // 좋아요
                jsonArray.add(member.getUserNickname());
                findBoard.setPostLikeList(jsonArray.toString());
                findBoard.setPostLike(findBoard.getPostLike()+1);
                freeBoardRepository.save(findBoard);
                log.info("좋아요");
            }
        }

        return "success";
    }
}
