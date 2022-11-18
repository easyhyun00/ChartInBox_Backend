package team2.chartBox.myPage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditMyPageResult {
    private String nickname;
    private String password;

    public EditMyPageResult() {
    }

    public EditMyPageResult(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
