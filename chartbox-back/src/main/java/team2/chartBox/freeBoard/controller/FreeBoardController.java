package team2.chartBox.freeBoard.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team2.chartBox.EmailConst;
import team2.chartBox.SessionConst;
import team2.chartBox.freeBoard.dto.FreeBoardDetailDto;
import team2.chartBox.freeBoard.dto.FreeBoardDto;
import team2.chartBox.freeBoard.dto.MovieInfoDto;
import team2.chartBox.freeBoard.dto.PostDetailDto;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.service.FreeBoardService;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class FreeBoardController {

    @Autowired
    private FreeBoardService freeBoardService;
    private MemberRepository memberRepository;

//    /*
//        무비 토크 - 전체 글 목록
//     */
//    @GetMapping("/movie-talk")
//    public ResponseEntity<FreeBoardDto> MovieTalkTotalPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
//        FreeBoardDto freeBoardDto = new FreeBoardDto();
//        freeBoardDto.setBoardList(freeBoardService.getMovieTalkList());
//
//        if (member == null) {
//            freeBoardDto.setUserNickname("");
//        } else {
//            freeBoardDto.setUserNickname(member.getUserNickname());
//        }
//
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
//    }

    /*
        무비 토크 - 전체 글 목록2
    */
    @GetMapping("/movie-talk")
    public ResponseEntity<FreeBoardDto> MovieTalkTotalPage() throws NullPointerException {

        // Member member = memberRepository.findByUserEmail("leejihyun0324@gmail.com");
        // Member member = memberRepository.findByUserEmail("leejihyun0324@gmail.com");

        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getMovieTalkList().subList(0,10));

//        if (member == null) {
//            freeBoardDto.setUserNickname("");
//        } else {
//            freeBoardDto.setUserNickname(member.getUserNickname());
//        }

        freeBoardDto.setUserNickname("잇타2피구현영상");

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

//    /*
//        무비 토크 - 자유 게시판 글 목록
//     */
//    @GetMapping("/movie-talk/free-board")
//    public ResponseEntity<FreeBoardDto> MovieTalkFreeBoardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
//        FreeBoardDto freeBoardDto = new FreeBoardDto();
//        freeBoardDto.setBoardList(freeBoardService.getFreeBoardList());
//
//        if (member == null) {
//            freeBoardDto.setUserNickname("");
//        } else {
//            freeBoardDto.setUserNickname(member.getUserNickname());
//        }
//
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
//    }

    /*
    무비 토크 - 자유 게시판 글 목록 2
 */
    @GetMapping("/movie-talk/free-board")
    public ResponseEntity<FreeBoardDto> MovieTalkFreeBoardPage() {

        // Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getFreeBoardList());

//        if (member == null) {
//            freeBoardDto.setUserNickname("");
//        } else {
//            freeBoardDto.setUserNickname(member.getUserNickname());
//        }

        freeBoardDto.setUserNickname("잇타2피구현영상");

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

//    /*
//        무비 토크 - 리뷰게시판 글 목록
//     */
//    @GetMapping("/movie-talk/review-board")
//    public ResponseEntity<FreeBoardDto> MovieTalkReviewBoardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
//        FreeBoardDto freeBoardDto = new FreeBoardDto();
//        freeBoardDto.setBoardList(freeBoardService.getReviewBoardList());
//
//        if (member == null) {
//            freeBoardDto.setUserNickname("");
//        } else {
//            freeBoardDto.setUserNickname(member.getUserNickname());
//        }
//
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
//    }

    /*
        무비 토크 - 리뷰게시판 글 목록2
    */
    @GetMapping("/movie-talk/review-board")
    public ResponseEntity<FreeBoardDto> MovieTalkReviewBoardPage() {

        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getReviewBoardList());

        if (member == null) {
            freeBoardDto.setUserNickname("");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }


//    /*
//       무비 토크 - Q&A 글 목록
//    */
//    @GetMapping("/movie-talk/qna-board")
//    public ResponseEntity<FreeBoardDto> MovieTalkQnaBoardPage(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) {
//        FreeBoardDto freeBoardDto = new FreeBoardDto();
//        freeBoardDto.setBoardList(freeBoardService.getQnaBoardList());
//
//        if (member == null) {
//            freeBoardDto.setUserNickname("");
//        } else {
//            freeBoardDto.setUserNickname(member.getUserNickname());
//        }
//
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
//    }

//    /*
//        무비 토크 - 게시물 상세 보기
//     */
//    @GetMapping("/movie-talk/{postId}")
//    public ResponseEntity<FreeBoardDetailDto> FreeBoardDetailPage(@PathVariable String postId,
//                                                                  @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member) throws IOException, ParseException {
//        FreeBoard freeBoardDetail = freeBoardService.getFreeBoardDetail(postId);
//
//        freeBoardService.PostViewCnt(freeBoardDetail); // 조회수 증가
//
//        PostDetailDto postDetailDto = new PostDetailDto(freeBoardDetail);
//
//        FreeBoardDetailDto freeBoardDetailDto = new FreeBoardDetailDto();
//        freeBoardDetailDto.setPostDetail(postDetailDto);
//        freeBoardDetailDto.setComments(freeBoardService.getCommentList(postId));
//        freeBoardDetailDto.setPostList(freeBoardService.getMovieTalkList());
//
//        if (member == null) {
//            freeBoardDetailDto.setUserNickname("");
//        } else {
//            freeBoardDetailDto.setUserNickname(member.getUserNickname());
//        }
//
//        if (postDetailDto.getMovieId().equals("")) {
//            log.info("영화 데이터 없음");
//        } else {
//            String movieId = postDetailDto.getMovieId();
//            log.info(movieId);
//            MovieInfoDto movieInfo = freeBoardService.findMovieInfo(movieId);
//            freeBoardDetailDto.setMovieInfo(movieInfo);
//        }
//
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        return new ResponseEntity<>(freeBoardDetailDto,headers, HttpStatus.OK);
//    }

    /*
   무비 토크 - Q&A 글 목록2
*/
    @GetMapping("/movie-talk/qna-board")
    public ResponseEntity<FreeBoardDto> MovieTalkQnaBoardPage() {

        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);

        FreeBoardDto freeBoardDto = new FreeBoardDto();
        freeBoardDto.setBoardList(freeBoardService.getQnaBoardList());

        if (member == null) {
            freeBoardDto.setUserNickname("");
        } else {
            freeBoardDto.setUserNickname(member.getUserNickname());
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDto,headers, HttpStatus.OK);
    }

    /*
    무비 토크 - 게시물 상세 보기
 */
    @GetMapping("/movie-talk/{postId}")
    public ResponseEntity<FreeBoardDetailDto> FreeBoardDetailPage(@PathVariable String postId) throws IOException, ParseException {

//        log.info(EmailConst.MEMBER_EMAIL.toString());
//
//        log.info(EmailConst.MEMBER_EMAIL.getClass().getName());
//
//        Member member = memberRepository.findByUserEmail(EmailConst.MEMBER_EMAIL);
//
//        log.info(member.getUserNickname());

        FreeBoard freeBoardDetail = freeBoardService.getFreeBoardDetail(postId);

        freeBoardService.PostViewCnt(freeBoardDetail); // 조회수 증가

        PostDetailDto postDetailDto = new PostDetailDto(freeBoardDetail);

        FreeBoardDetailDto freeBoardDetailDto = new FreeBoardDetailDto();
        freeBoardDetailDto.setPostDetail(postDetailDto);
        freeBoardDetailDto.setComments(freeBoardService.getCommentList(postId));
        freeBoardDetailDto.setPostList(freeBoardService.getMovieTalkList());

//        if (member == null) {
//            freeBoardDetailDto.setUserNickname("");
//        } else {
//            freeBoardDetailDto.setUserNickname(member.getUserNickname());
//        }

        freeBoardDetailDto.setUserNickname("잇타2피구현영상");

        if (postDetailDto.getMovieId().equals("")) {
            log.info("영화 데이터 없음");
        } else {
            String movieId = postDetailDto.getMovieId();
            log.info(movieId);
            MovieInfoDto movieInfo = freeBoardService.findMovieInfo(movieId);
            freeBoardDetailDto.setMovieInfo(movieInfo);
        }

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return new ResponseEntity<>(freeBoardDetailDto,headers, HttpStatus.OK);
    }


}
