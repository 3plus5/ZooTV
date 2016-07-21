package xmu.edu.a3plus5.zootv.dao;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.entity.User;

public interface HistoryDao {

    /*
    加入用户历史观看记录
     */
    public boolean addhistory(int userid, int rid);

    /*
    判断用户是否有观看记录
   */
    public boolean ifhavehistory(int userid, int rid);

    /*
    删除用户观看记录
    */
    public void deletehistory(int userid, int rid);

    /*
    查看我的历史记录
     */
    public List<Room> selehistoryRoom(int userid);

    /*
    清空缓存
    */
    public void wipecache(int userid);

    /*
   计算缓存大小
   */
    public String calcache(int userid);

}
