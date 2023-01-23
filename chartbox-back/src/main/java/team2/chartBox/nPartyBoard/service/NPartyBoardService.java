package team2.chartBox.nPartyBoard.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.entity.FreeBoardComment;
import team2.chartBox.member.entity.Member;
import team2.chartBox.nPartyBoard.dto.BoardWriteDto;
import team2.chartBox.nPartyBoard.dto.CommentDto;
import team2.chartBox.nPartyBoard.dto.NPartyBoardListDetailDto;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;
import team2.chartBox.nPartyBoard.entity.NPartyBoardComment;
import team2.chartBox.nPartyBoard.repository.NPartyBoardCommentRepository;
import team2.chartBox.nPartyBoard.repository.NPartyBoardRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class NPartyBoardService {

    @Autowired
    public NPartyBoardRepository nPartyBoardRepository;
    public NPartyBoardCommentRepository nPartyBoardCommentRepository;
    public ModelMapper mapper;

    /*
        n팟 구함 - 전체 글 목록
     */
    public List<NPartyBoardListDetailDto> getNPartyList() {
        List<NPartyBoard> boardList = nPartyBoardRepository.findAll(Sort.by(Sort.Direction.DESC, "postId"));

        List<NPartyBoardListDetailDto> returnList = boardList.stream()
                .map(post -> mapper.map(post,NPartyBoardListDetailDto.class))
                .collect(Collectors.toList());

        return returnList;
    }

    /*
        n팟 구함 - 카테고리 글 목록
     */
    public List<NPartyBoardListDetailDto> getNPartyCategoryList(String category) {
        List<NPartyBoard> boardList = nPartyBoardRepository.findAllByPostCategory(category,Sort.by(Sort.Direction.DESC, "postId"));

        List<NPartyBoardListDetailDto> returnList = boardList.stream()
                .map(post -> mapper.map(post,NPartyBoardListDetailDto.class))
                .collect(Collectors.toList());

        return returnList;
    }

    /*
        글 상세 보기
     */
    public NPartyBoard getBoardDetail(String postId) {
        return nPartyBoardRepository.findByPostId(Integer.valueOf(postId));
    }

    /*
        조회수 증가
     */
    public void PostViewCnt(NPartyBoard nPartyBoard) {
        nPartyBoard.setCountVisit(nPartyBoard.getCountVisit()+1);
        nPartyBoardRepository.save(nPartyBoard);
    }

    /*
        댓글 보기
     */
    public List<NPartyBoardComment> getCommentList(String postId) {
        return nPartyBoardCommentRepository.findAllByCmtPostId(Integer.valueOf(postId));
    }

    /*
        글 작성
     */
    public String PostWrite(BoardWriteDto boardWriteDto, Member member) {
        if (member == null)
            return "non-member"; // 비회원
        boardWriteDto.setPostUserNickname(member.getUserNickname());

        // dto 형식 바꾸고 db에 저장
        NPartyBoard nPartyBoard = new NPartyBoard(boardWriteDto);
        nPartyBoardRepository.save(nPartyBoard);

        return "success";
    }

    /*
        글 수정 페이지
     */
    public String EditPage(String postId, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
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
        글 수정 제출
     */
    public String EditBoard(String postId, BoardWriteDto boardWriteDto, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
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

            nPartyBoardRepository.save(findBoard);

            return "success";
        }
        return "member";
    }

    /*
        게시물 삭제
     */
    public String DeleteBoard(String postId, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (findBoard.getPostUserNickname().equals(member.getUserNickname())) {
            nPartyBoardRepository.delete(findBoard);
            return "success"; // 성공
        }
        return "member";
    }

    /*
        게시물 좋아요
     */
    public String LikeBoard(String postId, Member member) throws ParseException {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        String likeList = findBoard.getPostLikeList();

        if (likeList == null) { // 좋아요 회원 새로 추가
            JSONArray likeMemberList = new JSONArray();
            likeMemberList.add(member.getUserNickname());
            findBoard.setPostLikeList(likeMemberList.toString());
            findBoard.setPostLike(findBoard.getPostLike()+1);
            nPartyBoardRepository.save(findBoard);
            log.info(likeMemberList.toString());
        } else { // 이미 좋아요 회원 있는 경우
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(likeList);

            if (jsonArray.contains(member.getUserNickname())) { // 이미 좋아요 누름
                jsonArray.remove(member.getUserNickname());
                findBoard.setPostLikeList(jsonArray.toString());
                findBoard.setPostLike(findBoard.getPostLike()-1);
                nPartyBoardRepository.save(findBoard);
                log.info("좋아요 취소");
                return "cancel";
            } else { // 좋아요
                jsonArray.add(member.getUserNickname());
                findBoard.setPostLikeList(jsonArray.toString());
                findBoard.setPostLike(findBoard.getPostLike()+1);
                nPartyBoardRepository.save(findBoard);
                log.info("좋아요");
            }
        }
        return "success";
    }

    /*
        게시물 신고
     */
    public String ReportBoard(String postId, Member member) throws ParseException {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        String postReport = findBoard.getPostReport();
        if (postReport == null) { // 신고한 회원 새로 추가
            JSONArray reportMember = new JSONArray();
            reportMember.add(member.getUserNickname());
            findBoard.setPostReport(reportMember.toString());
            nPartyBoardRepository.save(findBoard);
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
                nPartyBoardRepository.delete(findBoard);
                log.info("게시물 삭제");
                return "delete";

            } else {
                findBoard.setPostReport(jsonArray.toString());
                nPartyBoardRepository.save(findBoard);
                log.info(jsonArray.toString());
            }

        }
        return "success";
    }

    /*
        댓글 작성
     */
    public String AddComment(String postId, CommentDto commentDto, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (commentDto.getCmtUserNickname().equals(member.getUserNickname())) {
            NPartyBoardComment nPartyBoardComment = new NPartyBoardComment(commentDto,postId);
            nPartyBoardCommentRepository.save(nPartyBoardComment);

            findBoard.setPostComment(findBoard.getPostComment()+1);
            nPartyBoardRepository.save(findBoard);

            return "success";
        }
        return "member"; // 잘못된 사용자
    }

    /*
        댓글 수정
     */
    public String EditComment(String postId, String cmtId, CommentDto commentDto, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (cmtId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoardComment findComment = nPartyBoardCommentRepository.findByCmtId(Integer.valueOf(cmtId));
        if (findComment == null) {
            return "comment"; // 없는 댓글
        }
        if (findComment.getCmtUserNickname().equals(member.getUserNickname())) {

            // 댓글 수정
            findComment.setCmtContent(commentDto.getCmtContent());

            nPartyBoardCommentRepository.save(findComment);

            return "success";
        }
        return "member"; // 잘못된 사용자
    }

    /*
        댓글 삭제
     */
    public String DeleteComment(String postId, String cmtId, Member member) {
        if (postId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoard findBoard = getBoardDetail(postId);
        if (findBoard == null) {
            return "board"; // 없는 게시물
        }
        if (member == null) {
            return "non-member"; // 없는 회원(비회원)
        }
        if (cmtId == null) {
            return "pathVariable"; // 잘못된 url 경로
        }
        NPartyBoardComment findComment = nPartyBoardCommentRepository.findByCmtId(Integer.valueOf(cmtId));
        if (findComment == null) {
            return "comment"; // 없는 댓글
        }
        if (findComment.getCmtUserNickname().equals(member.getUserNickname())) {
            // 댓글 삭제
            nPartyBoardCommentRepository.delete(findComment);

            findBoard.setPostComment(findBoard.getPostComment()-1);
            nPartyBoardRepository.save(findBoard);

            return "success";
        }
        return "member"; // 잘못된 사용자
    }
}
