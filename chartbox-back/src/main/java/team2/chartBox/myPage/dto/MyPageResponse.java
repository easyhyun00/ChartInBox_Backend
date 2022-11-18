package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MyPageResponse {
    private String userEmail;
    private String userNickname;

    public MyPageResponse() {
    }

    public MyPageResponse(String userEmail, String userNickname) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
    }
}
