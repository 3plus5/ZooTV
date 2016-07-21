package xmu.edu.a3plus5.zootv.dao.daoimpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import xmu.edu.a3plus5.zootv.dao.UserDao;
import xmu.edu.a3plus5.zootv.db.MySQLiteOpenHelper;
import xmu.edu.a3plus5.zootv.entity.User;
import xmu.edu.a3plus5.zootv.util.DBUtil;


public class UserDaoImpl implements UserDao {
    private MySQLiteOpenHelper helper;
    private static UserDao userdao;

    public UserDaoImpl(Context context) {
        helper = new MySQLiteOpenHelper(context);
    }

    // synchronized同步 // 单例设计模式
    public static synchronized UserDao getInit(Context context) {
        if (userdao == null) {
            userdao = new UserDaoImpl(context);
        }
        return userdao;
    }

    public void close(SQLiteDatabase db, Cursor cur) {
        if (cur != null) {
            cur.close();
        }
        if (db != null) {
            db.close();
        }
    }

    public void close(SQLiteDatabase db) {
        if (db != null) {
            db.close();
        }
    }

    @Override
    public synchronized boolean addUserbyItem(String userPic, String userName) {
        boolean flag = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userPic, userPic);
        values.put(DBUtil.userName, userName);
        values.put(DBUtil.signCount, 0);
        values.put(DBUtil.lastSignDate, "2000-01-01");
        long i = 0;
        i = db.insert(DBUtil.User_TABLE_NAME, null, values);
        if (i != 0)
            flag = true;
        close(db);
        return flag;
    }

    @Override
    public synchronized User addUserbyUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userPic, user.getUserPic());
        values.put(DBUtil.userName, user.getUserName());
        values.put(DBUtil.signCount, user.getSignCount());
        values.put(DBUtil.lastSignDate, user.getLastSignDate());
        long i = 0;
        i = db.insert(DBUtil.User_TABLE_NAME, null, values);
        User myuser = selectuser(user);
        close(db);
        return myuser;
    }

    public synchronized User selectuser(User user) {
        User myuser = null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.userId, DBUtil.userName, DBUtil.userPic, DBUtil.signCount, DBUtil.lastSignDate};
        String selection = DBUtil.userName + "='" + user.getUserName() + "' and " + DBUtil.userPic + "='" + user.getUserPic() + "'";
        Cursor cur = db.query(DBUtil.User_TABLE_NAME, columns, selection, null, null, null, null, null);
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                int userid = cur.getInt(cur.getColumnIndexOrThrow(DBUtil.userId));
                int signcount = cur.getInt(cur.getColumnIndexOrThrow(DBUtil.signCount));
                String lastSignDate = cur.getString(cur.getColumnIndexOrThrow(DBUtil.lastSignDate));
                myuser = new User(userid, user.getUserPic(), user.getUserName(), signcount, lastSignDate);
            } while (cur.moveToNext());
        }
        close(db, cur);
        return myuser;
    }

    @Override
    public synchronized int searchSignCount(int userid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.signCount};
        String selection = DBUtil.userId + "=" + userid;
        Cursor cur = db.query(DBUtil.User_TABLE_NAME, columns, selection, null, null, null, null, null);
        int signCount = 0;
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            signCount = cur.getInt(cur.getColumnIndexOrThrow(DBUtil.signCount));
        }
        close(db, cur);

        return signCount;
    }

    @Override
    public synchronized String searchLastSignDate(int userid) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.lastSignDate};
        String selection = DBUtil.userId + "=" + userid;
        Cursor cur = db.query(DBUtil.User_TABLE_NAME, columns, selection, null, null, null, null, null);
        String lastSignDate = "2000-01-01";
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            lastSignDate = cur.getString(cur.getColumnIndexOrThrow(DBUtil.lastSignDate));
        }
        close(db, cur);

        return lastSignDate;
    }

    @Override
    public synchronized boolean updateSignCount(int userid, int signCount) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.signCount, signCount);
        String whereClause = DBUtil.userId + "=?";
        String[] whereargs = {String.valueOf(userid)};
        db.update(DBUtil.User_TABLE_NAME, values, whereClause, whereargs);
        return true;
    }

    @Override
    public synchronized boolean updateSignDate(int userid, String lastSignDate) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.lastSignDate, lastSignDate);
        String whereClause = DBUtil.userId + "=?";
        String[] whereargs = {String.valueOf(userid)};
        db.update(DBUtil.User_TABLE_NAME, values, whereClause, whereargs);
        return false;
    }

}
