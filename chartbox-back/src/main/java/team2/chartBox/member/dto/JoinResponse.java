package team2.chartBox.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinResponse {
    private String userEmail;
    private String userPassword;
    private String userNickname;

    public JoinResponse() {
    }

    public JoinResponse(String userEmail, String userPassword, String userNickname) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
    }
}
