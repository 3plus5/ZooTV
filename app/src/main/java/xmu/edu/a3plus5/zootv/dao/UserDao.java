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
    添加我的选择标签
    */
    public boolean addlabel(int userid,String[] labels);

    /*
    查看我的所有选择标签
    */
    public List<String> selelabels(int userid);

    /*
    添加房间记录
    */
    public Room addRoom(Room room);
    /*
    判断房间是否存在
    */
    public boolean ifhaveRoom(Room room);
    /*
    根据信息获取房间
     */
    public Room selectroom(Room room);

}
