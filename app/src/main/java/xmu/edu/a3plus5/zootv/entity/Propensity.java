package xmu.edu.a3plus5.zootv.entity;

import java.io.Serializable;

public class Propensity implements Serializable {

    private int userId;
    private String label;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Propensity() {
    }

    public Propensity(int userId, String label) {
        this.userId = userId;
        this.label = label;
    }

    @Override
    public String toString() {
        return "Propensity{" +
                "userId=" + userId +
                ", label='" + label + '\'' +
                '}';
    }
}
