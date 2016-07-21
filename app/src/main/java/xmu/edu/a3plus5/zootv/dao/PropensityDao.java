package xmu.edu.a3plus5.zootv.dao;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.entity.User;

public interface PropensityDao {

    /*
    添加我的选择标签
    */
    public boolean addlabel(int userid, List<String> labels);

    /*
    删除用户所有标签
    */
    public boolean deletelabels(int userid);

    /*
    查看我的所有选择标签
    */
    public List<String> selelabels(int userid);

}
