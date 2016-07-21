package xmu.edu.a3plus5.zootv.dao;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.History;
import xmu.edu.a3plus5.zootv.entity.Interest;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.entity.User;

public interface UserDao {
    /*
    第三方登录
     */
    public boolean addUserbyItem(String userPic, String userName);

    /*
    第三方登录2
     */
    public User addUserbyUser(User user);

    /*
    根据信息获取用户
     */
    public User selectuser(User user);


    /**
     * 获取签到次数
     */
    public int searchSignCount(int userid);

    /**
     * 获取上次签到日期
     */
    public String searchLastSignDate(int userid);

    /**
     * 更新签到次数
     */
    public boolean updateSignCount(int userid, int signCount);

    /**
     * 更新签到日期
     */
    public boolean updateSignDate(int userid, String lastSignDate);
}
