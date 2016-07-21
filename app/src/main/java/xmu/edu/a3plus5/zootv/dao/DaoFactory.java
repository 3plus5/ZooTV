package xmu.edu.a3plus5.zootv.dao;

import android.content.Context;

import xmu.edu.a3plus5.zootv.dao.daoimpl.HistoryDaoImpl;
import xmu.edu.a3plus5.zootv.dao.daoimpl.InterestDaoImpl;
import xmu.edu.a3plus5.zootv.dao.daoimpl.PropensityDaoImpl;
import xmu.edu.a3plus5.zootv.dao.daoimpl.RoomDaoImpl;
import xmu.edu.a3plus5.zootv.dao.daoimpl.UserDaoImpl;

public class DaoFactory {

    public static UserDao getUserDao(Context context) {
        return UserDaoImpl.getInit(context);
    }
    public static InterestDao getInterestDao(Context context) {
        return InterestDaoImpl.getInit(context);
    }
    public static HistoryDao getHistoryDao(Context context) {
        return HistoryDaoImpl.getInit(context);
    }
    public static RoomDao getRoomDao(Context context) {
        return RoomDaoImpl.getInit(context);
    }
    public static PropensityDao getPropensityDao(Context context) {
        return PropensityDaoImpl.getInit(context);
    }
}
