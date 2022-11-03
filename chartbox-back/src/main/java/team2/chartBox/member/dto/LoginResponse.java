package team2.chartBox.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginResponse {
    private String userEmail;
    private String userPassword;

    public LoginResponse() {
    }

    public LoginResponse(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}


