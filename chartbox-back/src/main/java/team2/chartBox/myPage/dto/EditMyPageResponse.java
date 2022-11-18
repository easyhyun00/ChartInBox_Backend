package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditMyPageResponse {

    private String userEmail; // 현재 이메일
    private String newNickname; // 바꿀 닉네임
    private String userPassword; // 현재 비밀번호
    private String newPassword; // 바꿀 비밀번호

    public EditMyPageResponse() {
    }

    public EditMyPageResponse(String userEmail, String newNickname, String userPassword, String newPassword) {
        this.userEmail = userEmail;
        this.newNickname = newNickname;
        this.userPassword = userPassword;
        this.newPassword = newPassword;
    }
}
