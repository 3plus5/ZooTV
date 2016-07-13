package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

/**
 * Created by hd_chen on 2016/7/13.
 */
public class User implements Serializable{
    String userId;
    String userName;
    String userPhoto;

    public User() {
    }

    public User(String userId,String userName, String user_photo) {
        this.userId = userId;
        this.userName = userName;
        this.userPhoto = user_photo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPhoto='" + userPhoto + '\'' +
                '}';
    }
}
