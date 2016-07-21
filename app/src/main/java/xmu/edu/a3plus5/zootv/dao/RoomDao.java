package xmu.edu.a3plus5.zootv.dao;

import java.util.List;

import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.entity.User;

public interface RoomDao {

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
