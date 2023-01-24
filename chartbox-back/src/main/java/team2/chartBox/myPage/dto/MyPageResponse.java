package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyPageResponse {
    private String userEmail;
    private String userNickname;
    private Object boardList;
    private Object commentList;
    private Object scrapList;

    public MyPageResponse() {
    }
}
