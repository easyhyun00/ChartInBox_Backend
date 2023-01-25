package team2.chartBox.myPage.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import team2.chartBox.freeBoard.entity.FreeBoard;
import team2.chartBox.freeBoard.entity.FreeBoardComment;
import team2.chartBox.freeBoard.repository.FreeBoardCommentRepository;
import team2.chartBox.freeBoard.repository.FreeBoardRepository;
import team2.chartBox.member.entity.Member;
import team2.chartBox.member.repository.MemberRepository;
import team2.chartBox.movieApi.dto.MvScrapDto;
import team2.chartBox.movieApi.service.FindMvService;
import team2.chartBox.myPage.dto.EditNicknameResponse;
import team2.chartBox.myPage.dto.EditPasswordResponse;
import team2.chartBox.myPage.dto.MyBoardListDto;
import team2.chartBox.myPage.dto.MyCommentListDto;
import team2.chartBox.nPartyBoard.entity.NPartyBoard;
import team2.chartBox.nPartyBoard.repository.NPartyBoardRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MyPageService {

    @Autowired
    private MemberRepository memberRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private FreeBoardRepository freeBoardRepository;
    private FreeBoardCommentRepository freeBoardCommentRepository;
    private FindMvService findMvService;
    public ModelMapper mapper;

    /*
        닉네임 변경
     */
    public String editNickname(EditNicknameResponse editNicknameRes) {
        if (memberRepository.findByUserNickname(editNicknameRes.getNewNickname()) != null) {
            // 이미 사용중인 닉네임
            return "fail";
        }

        // 변경할 member 찾아, 닉네임 변경 후 DB에 저장
        Member findMember = memberRepository.findByUserEmail(editNicknameRes.getUserEmail());
        findMember.setUserNickname(editNicknameRes.getNewNickname());
        memberRepository.save(findMember);
        return "success";
    }

    /*
        비밀번호 변경
     */
    public String editPassword(EditPasswordResponse editPasswordRes) {

        Member findMember = memberRepository.findByUserEmail(editPasswordRes.getUserEmail());

        if (bCryptPasswordEncoder.matches(editPasswordRes.getUserPassword(), findMember.getUserPassword())) {
            log.info("변경될 비밀번호 {}",editPasswordRes.getNewPassword());
            String encPassword = bCryptPasswordEncoder.encode(editPasswordRes.getNewPassword()); // 비밀번호 암호화
            findMember.setUserPassword(encPassword);
            memberRepository.save(findMember);
            return "success";
        }
        // 잘못 입력된 비밀번호
        return "fail";
    }

    /*
        회원 탈퇴
     */
    public String withdrawMember(Member member) {
        Member findMember = memberRepository.findByUserEmail(member.getUserEmail());
        if (findMember == null)
            return "fail";
        memberRepository.delete(findMember);
        return "success";
    }

    /*
        회원이 작성한 글 목록
     */
    public List<MyBoardListDto> getBoardList(String userNickname) {

        List<MyBoardListDto> list = new ArrayList<>();

        List<FreeBoard> boardList = freeBoardRepository.findAllByPostUserNickname(userNickname, Sort.by(Sort.Direction.DESC, "postId"));

        for (int i = 0; i < boardList.size(); i++) {
            FreeBoard board = boardList.get(i);
            MyBoardListDto myBoardListDto = new MyBoardListDto(board);
            list.add(myBoardListDto);
        }

        return list;
    }

    /*
        회원이 작성한 댓글 목록
     */
    public List<MyCommentListDto> getCommentList(String userNickname) {

        List<MyCommentListDto> list = new ArrayList<>();

        List<FreeBoardComment> findComment = freeBoardCommentRepository.findAllByCmtUserNickname(userNickname,Sort.by(Sort.Direction.DESC, "cmtId"));

        for (int i = 0; i < findComment.size(); i++) {
            FreeBoardComment comment = findComment.get(i);
            MyCommentListDto myCommentListDto = new MyCommentListDto();

            myCommentListDto.setCmtContent(comment.getCmtContent());
            myCommentListDto.setCmtPostId(comment.getCmtPostId());
            myCommentListDto.setCmtDate(comment.getCmtDate().toString());
            myCommentListDto.setCmtId(comment.getCmtId());
            FreeBoard findBoard = freeBoardRepository.findByPostId(comment.getCmtPostId());
            myCommentListDto.setBoardTitle(findBoard.getPostTitle());

            list.add(myCommentListDto);
        }
        return list;
    }


    /*
        스크랩 영화 목록
     */
    public List<MvScrapDto> getScrapMovieList(String userNickname) throws ParseException, IOException {

        JSONParser jsonParser = new JSONParser();
        Member findMember = memberRepository.findByUserNickname(userNickname);
        String movieScrap = findMember.getUserMovieScrap();
        JSONArray jsonArray = (JSONArray) jsonParser.parse(movieScrap);

        List<MvScrapDto> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String movieId = jsonArray.get(i).toString();
            log.info(movieId);
            MvScrapDto mvScrap = findMvService.findByScrap(movieId);
            list.add(mvScrap);
        }
        return list;
    }
}
