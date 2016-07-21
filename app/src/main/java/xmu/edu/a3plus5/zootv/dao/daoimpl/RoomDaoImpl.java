package xmu.edu.a3plus5.zootv.dao.daoimpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xmu.edu.a3plus5.zootv.dao.RoomDao;
import xmu.edu.a3plus5.zootv.db.MySQLiteOpenHelper;
import xmu.edu.a3plus5.zootv.entity.Room;
import xmu.edu.a3plus5.zootv.util.DBUtil;


public class RoomDaoImpl implements RoomDao {
    private MySQLiteOpenHelper helper;
    private static RoomDao roomdao;

    public RoomDaoImpl(Context context) {
        helper = new MySQLiteOpenHelper(context);
    }

    // synchronized同步 // 单例设计模式
    public static synchronized RoomDao getInit(Context context) {
        if (roomdao == null) {
            roomdao = new RoomDaoImpl(context);
        }
        return roomdao;
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


    public synchronized Room addRoom(Room room) {
        boolean flag = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.platform, room.getPlatform());
        values.put(DBUtil.roomId, room.getRoomId());
        long i = 0;
        i = db.insert(DBUtil.Room_TABLE_NAME, null, values);
        Room myroom = selectroom(room);
        close(db);
        return myroom;
    }

    public synchronized boolean ifhaveRoom(Room room) {
        boolean flag = false;
        List<Room> myrooms = new ArrayList<Room>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.rid};
        String selection = DBUtil.platform + "= '" + room.getPlatform() + "' and " + DBUtil.roomId + "='" + room.getRoomId() + "'";
        Cursor cur = db.query(DBUtil.Room_TABLE_NAME, columns, selection, null, null, null, null, null);
        if (cur.getCount() != 0)
            flag = true;
        close(db, cur);
        return flag;
    }

    public synchronized Room selectroom(Room room) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.rid, DBUtil.platform, DBUtil.roomId};
        String selection = DBUtil.platform + "='" + room.getPlatform() + "' and " + DBUtil.roomId + "='" + room.getRoomId() + "'";
        Cursor cur = db.query(DBUtil.Room_TABLE_NAME, columns, selection, null, null, null, null, null);
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                int rid = cur.getInt(cur.getColumnIndexOrThrow(DBUtil.rid));
                room.setRid(rid);
            } while (cur.moveToNext());
        }
        close(db, cur);
        return room;
    }

}
