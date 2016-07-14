package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

public class Interest implements Serializable{
    private int interestId;     //关注ID
    private int userId;         //用户编号ID
    private int roomId;        //房间编号id

    public int getInterestId() {
        return interestId;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Interest(int interestId, int userId, int roomId) {
        this.interestId = interestId;
        this.userId = userId;
        this.roomId = roomId;
    }

    public Interest(int userId, int roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public Interest() {
    }

    @Override
    public String toString() {
        return "Interest{" +
                "interestId=" + interestId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                '}';
    }
}