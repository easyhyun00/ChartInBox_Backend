package team2.chartBox.freeBoard.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.dto.*;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.entity.FreeBoardComment;
import team2.chartBox.freeBoard.repository.FreeBoardCommentRepository;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.member.entity.Member;
import team2.chartBox.movieApi.dto.MvApiDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

    /*
        영화 데이터 불러오기
     */
    private final String Mv_api_url =
            "https://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y&ServiceKey=XHK4HN18GQY1JVK85RO1&listCount=30";

    public MovieInfoDto findMovieInfo(String mvId) throws IOException, ParseException {

        log.info("영화 상세 보기 {}",mvId);

        MovieInfoDto movieInfoDto = new MovieInfoDto();

        StringBuilder urlBuilder = new StringBuilder(Mv_api_url);

        String movieId = mvId.substring(0, 1);
        String movieSeq = mvId.substring(1);

        urlBuilder.append("&" + URLEncoder.encode("movieId","UTF-8") + "=" + URLEncoder.encode(movieId, "UTF-8"))
                .append("&" + URLEncoder.encode("movieSeq","UTF-8") + "=" + URLEncoder.encode(movieSeq, "UTF-8"));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        JSONObject result = null;

        result  = (JSONObject) new JSONParser().parse(sb.toString());

        JSONObject data = (JSONObject) ((JSONArray) result.get("Data")).get(0);

        JSONArray array = (JSONArray) data.get("Result");

        JSONObject tmp;
        for (int i = 0; i < array.size(); i++) {
            tmp = (JSONObject) array.get(i);

            String title = (String) tmp.get("title");
            String genre = (String) tmp.get("genre");
            String runtime = (String) tmp.get("runtime");
            String rating = (String) tmp.get("rating");
            String posters = ((String) tmp.get("posters"));
            int idx = posters.indexOf("|");
            String poster;
            if (idx > 0) {
                poster = posters.substring(0, idx);
            } else {
                poster = posters;
            }
            movieInfoDto.setMvPoster(poster);
            movieInfoDto.setMvTitle(title);
            movieInfoDto.setMvGenre(genre);
            movieInfoDto.setMvRuntime(runtime);
            movieInfoDto.setMvRating(rating);
        }

        rd.close();
        conn.disconnect();

        return movieInfoDto;
    }
}
