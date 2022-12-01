package com.example.mypet;
// 계정 정보 클래스
public class UserAccount {
    private String idToken; // Firebase Uid (고유 토큰 정보)
    private String emailId;
    private String password;
    private String nickname;

    public UserAccount() { } // Firebase에서 빈 생성자 필요

    public String getIdToken() {return idToken;}
    public void setIdToken(String idToken) {this.idToken = idToken;}

    public String getEmailId() {return emailId;}
    public void setEmailId(String emailId) {this.emailId = emailId;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}
}
