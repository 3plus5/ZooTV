package xmu.edu.a3plus5.zootv.dao;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.entity.User;

public interface InterestDao {

    /*
    用户关注兴趣
     */
    public boolean addinterest(int userid, int rid);

    /*
    判断用户是否关注兴趣
    */
    public boolean ifhaveinterest(int userid, int rid);

    /*
    删除用户关注兴趣
    */
    public void deleteinterest(int userid, int rid);

    /*
    查看我的关注兴趣
     */
    public List<Room> seleinterestRoom(int userid);

}
