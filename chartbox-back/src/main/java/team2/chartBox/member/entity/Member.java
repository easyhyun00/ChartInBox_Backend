package team2.chartBox.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team2.chartBox.member.dto.JoinResponse;
import team2.chartBox.member.dto.LoginResponse;

import javax.persistence.*;

@Table(name = "membertest")
@Entity
@Getter @Setter
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERID")
    private Integer userId;

    @Column(name = "USEREMAIL")
    private String userEmail;

    @Column(name = "USERPASSWORD")
    private String userPassword;

    @Column(name = "USERNICKNAME")
    private String userNickname;

    @Column(name = "AUTHKEY")
    private String authKey;

    @Column(name = "AUTHSTATUS")
    private Integer authStatus;


    // 생성자
    public Member() {
    }

    public Member(String userEmail, String userPassword, String userNickname) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.authKey = "";
        this.authStatus = 0;
    }

    public Member(JoinResponse joinResponse) {
        this.userEmail = joinResponse.getUserEmail();
        this.userPassword = joinResponse.getUserPassword();
        this.userNickname = joinResponse.getUserNickname();
        this.authKey = "";
        this.authStatus = 0;
    }

}
