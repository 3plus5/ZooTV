package xmu.edu.a3plus5.zootv.dao;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.History;
import xmu.edu.a3plus5.zootv.entity.Interest;
import xmu.edu.a3plus5.zootv.entity.User;

public interface UserDao {
    /*
    第三方登录
     */
    public boolean addUserbyItem(String userPic, String userName);
    /*
    第三方登录2
     */
    public boolean addUserbyUser(User user);
    /*
    用户关注兴趣
     */
    public boolean addinterest(int userid, int rid);
    /*
    查看我的关注兴趣
     */
    public List<Interest> seleinterest(int userid);
    /*
    加入用户历史观看记录
     */
    public boolean addhistory(int userid, int rid);
    /*
    查看我的历史记录
     */
    public List<History> selehistory(int userid);


    

}
