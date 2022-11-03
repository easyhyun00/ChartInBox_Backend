package team2.chartBox.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailResponse {
    private String userEmail;

    public UserEmailResponse() {
    }

    public UserEmailResponse(String userEmail) {
        this.userEmail = userEmail;
    }
}
