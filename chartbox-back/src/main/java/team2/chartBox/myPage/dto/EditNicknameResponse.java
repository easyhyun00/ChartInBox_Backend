package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditNicknameResponse {
    private String userEmail; // 현재 이메일
    private String newNickname; // 바꿀 닉네임

    public EditNicknameResponse() {
    }

    public EditNicknameResponse(String userEmail, String newNickname) {
        this.userEmail = userEmail;
        this.newNickname = newNickname;
    }
}
