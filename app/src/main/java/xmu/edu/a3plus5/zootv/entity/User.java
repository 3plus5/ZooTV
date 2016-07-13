package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

public class User implements Serializable{
    private int userId;       //用户编号ID
    private String userPic;   //第三方账号头像
    private String userName;  //第三方账号名
    private String userPwd;    //第三方加密账号密码
    private int signCount;    //用户签到次数
    private int logCount;     //用户登录次数

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public int getLogCount() {
        return logCount;
    }

    public void setLogCount(int logCount) {
        this.logCount = logCount;
    }

    public User( String userPic, String userName, String userPwd, int signCount, int logCount) {
        this.userPic = userPic;
        this.userName = userName;
        this.userPwd = userPwd;
        this.signCount = signCount;
        this.logCount = logCount;
    }

    public User(String userPic, String userName, String userPwd) {
        this.userPic = userPic;
        this.userName = userName;
        this.userPwd = userPwd;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPic='" + userPic + '\'' +
                ", userName='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", signCount=" + signCount +
                ", logCount=" + logCount +
                '}';
    }
}