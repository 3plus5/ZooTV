package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;       //用户编号ID
    private String userPic;   //第三方账号头像
    private String userName;  //第三方账号名
    private int signCount;    //用户签到次数
    private String lastSignDate;   //用户上次签到日期

    public User() {
    }

    public User(int userId, String userPic, String userName, int signCount, String lastSignDate) {
        this.userId = userId;
        this.userPic = userPic;
        this.userName = userName;
        this.signCount = signCount;
        this.lastSignDate = lastSignDate;
    }

    public User(String userPic, String userName) {
        this.userPic = userPic;
        this.userName = userName;
        this.signCount = 0;
        this.lastSignDate = "2000-01-01";
    }

    public User(String userPic, String userName, int signCount, String lastSignDate) {
        this.userPic = userPic;
        this.userName = userName;
        this.signCount = signCount;
        this.lastSignDate = lastSignDate;
    }

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

    public int getSignCount() {
        return signCount;
    }

    public void setSignCount(int signCount) {
        this.signCount = signCount;
    }

    public String getLastSignDate() {
        return lastSignDate;
    }

    public void setLastSignDate(String lastSignDate) {
        this.lastSignDate = lastSignDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userPic='" + userPic + '\'' +
                ", userName='" + userName + '\'' +
                ", signCount=" + signCount +
                ", lastSignDate=" + lastSignDate +
                '}';
    }
}
