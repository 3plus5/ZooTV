package xmu.edu.a3plus5.zootv.dao.daoimpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xmu.edu.a3plus5.zootv.dao.InterestDao;
import xmu.edu.a3plus5.zootv.db.MySQLiteOpenHelper;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.util.DBUtil;


public class InterestDaoImpl implements InterestDao {
    private MySQLiteOpenHelper helper;
    private static InterestDao interestdao;

    public InterestDaoImpl(Context context) {
        helper = new MySQLiteOpenHelper(context);
    }

    // synchronized同步 // 单例设计模式
    public static synchronized InterestDao getInit(Context context) {
        if (interestdao == null) {
            interestdao = new InterestDaoImpl(context);
        }
        return interestdao;
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
    public synchronized boolean addinterest(int userid, int rid) {
        boolean flag = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userId, userid);
        values.put(DBUtil.rid, rid);
        long i = 0;
        i = db.insert(DBUtil.Interest_TABLE_NAME, null, values);
        if (i != 0)
            flag = true;
        close(db);
        return flag;
    }

    public synchronized boolean ifhaveinterest(int userid, int rid) {
        boolean flag = false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.interestId};
        String selection = DBUtil.userId + "=" + userid + " and " + DBUtil.rid + "=" + rid;
        Cursor cur = db.query(DBUtil.Interest_TABLE_NAME, columns, selection, null, null, null, null, null);
        if (cur.getCount() != 0)
            flag = true;
        close(db, cur);
        return flag;
    }

    public synchronized void deleteinterest(int userid, int rid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = DBUtil.userId + "=? and " + DBUtil.rid + "=?";
        String[] whereargs = {String.valueOf(userid), String.valueOf(rid)};
        db.delete(DBUtil.Interest_TABLE_NAME, whereClause, whereargs);
    }

    public synchronized List<Room> seleinterestRoom(int userid) {
        List<Integer> rids = new ArrayList<Integer>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.rid};
        String selection = DBUtil.userId + "=" + userid;
        Cursor cur = db.query(DBUtil.Interest_TABLE_NAME, columns, selection, null, null, null, null, null);
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                int rId = cur.getInt(cur.getColumnIndexOrThrow(DBUtil.rid));
                rids.add(rId);
            } while (cur.moveToNext());
        }

        List<Room> myrooms = new ArrayList<Room>();
        for (int i = 0; i < rids.size(); i++) {
            String columns2[] = {DBUtil.platform, DBUtil.roomId};
            String selection2 = DBUtil.rid + "=" + rids.get(i);
            cur = db.query(DBUtil.Room_TABLE_NAME, columns2, selection2, null, null, null, null, null);
            if (cur.getCount() != 0) {
                cur.moveToFirst();
                do {
                    String platform = cur.getString(cur.getColumnIndexOrThrow(DBUtil.platform));
                    String roomId = cur.getString(cur.getColumnIndexOrThrow(DBUtil.roomId));
                    Room myroom = new Room(rids.get(i), platform, roomId);
                    myrooms.add(myroom);
                } while (cur.moveToNext());
            }
        }
        close(db, cur);
        return myrooms;
    }
}
