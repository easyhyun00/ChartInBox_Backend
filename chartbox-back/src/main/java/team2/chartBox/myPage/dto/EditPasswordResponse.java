package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditPasswordResponse {

    private String userEmail; // 현재 이메일
    private String userPassword; // 현재 비밀번호
    private String newPassword; // 바꿀 비밀번호

    public EditPasswordResponse() {
    }

    public EditPasswordResponse(String userEmail, String userPassword, String newPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.newPassword = newPassword;
    }
}
