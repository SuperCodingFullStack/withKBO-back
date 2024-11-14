package study.withkbo.UserLoginLogout.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;

@Entity
public class UserEntity {

    @Id // 프라이머리 키 지정
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userIdx;

    @Column(nullable = false, length = 30)
    private String uEmail;

    @Column(nullable = false, length = 30)
    private String uPwd;

    @Column(nullable = false, length = 30)
    private String uName;

    @Column(nullable = true, length = 30)
    private String uNickname;

    @Column(nullable = false, length = 20)
    private String uPhone;

    @Column(nullable = false)
    private Boolean uPhoneAuth;

    @Column(nullable = false, length = 30)
    private String uAddress;

    @Column(nullable = false)
    private String uStatus;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Date deletedAt;

    @Column(nullable = false, length = 150)
    private String ProfileImg;

    public UserEntity() {}

    public int getUserIdx() {
        return userIdx;
    }

    public String getuEmail() {
        return uEmail;
    }

    public String getuPwd() {
        return uPwd;
    }

    public String getuName() {
        return uName;
    }

    public String getuNickname() {
        return uNickname;
    }

    public String getuPhone() {
        return uPhone;
    }

    public Boolean getuPhoneAuth() {
        return uPhoneAuth;
    }

    public String getuAddress() {
        return uAddress;
    }

    public String getuStatus() {
        return uStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public String getProfileImg() {
        return ProfileImg;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setuNickname(String uNickname) {
        this.uNickname = uNickname;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }

    public void setuPhoneAuth(Boolean uPhoneAuth) {
        this.uPhoneAuth = uPhoneAuth;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setProfileImg(String profileImg) {
        ProfileImg = profileImg;
    }
}
