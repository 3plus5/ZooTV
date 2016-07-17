package xmu.edu.a3plus5.zootv.dao;

import android.content.Context;

import xmu.edu.a3plus5.zootv.util.DBUtil;

public class DaoFactory {

    public static UserDao getUserDao(Context context) {
        return UserDaoImpl.getInit(context);
    }
}
