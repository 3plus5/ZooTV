package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

public class History implements Serializable{
    private int historyId;     //历史记录ID
    private int userId;         //用户编号ID
    private int rId;         //房间编号id

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return rId;
    }

    public void setRoomId(int roomId) {
        this.rId = roomId;
    }

    public History(int historyId, int userId, int roomId) {
        this.historyId = historyId;
        this.userId = userId;
        this.rId = roomId;
    }

    public History(int userId, int roomId) {
        this.userId = userId;
        this.rId = roomId;
    }

    public History() {
    }

    @Override
    public String toString() {
        return "History{" +
                "historyId=" + historyId +
                ", userId=" + userId +
                ", roomId=" + rId +
                '}';
    }
}