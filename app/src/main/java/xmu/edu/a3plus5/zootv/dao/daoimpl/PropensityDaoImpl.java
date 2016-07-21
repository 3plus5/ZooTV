package xmu.edu.a3plus5.zootv.dao.daoimpl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xmu.edu.a3plus5.zootv.dao.PropensityDao;
import xmu.edu.a3plus5.zootv.db.MySQLiteOpenHelper;
import xmu.edu.a3plus5.zootv.util.DBUtil;


public class PropensityDaoImpl implements PropensityDao {
    private MySQLiteOpenHelper helper;
    private static PropensityDao propensitydao;

    public PropensityDaoImpl(Context context) {
        helper = new MySQLiteOpenHelper(context);
    }

    // synchronized同步 // 单例设计模式
    public static synchronized PropensityDao getInit(Context context) {
        if (propensitydao == null) {
            propensitydao = new PropensityDaoImpl(context);
        }
        return propensitydao;
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


    public synchronized boolean addlabel(int userid, List<String> labels) {
        boolean flag = true;
        SQLiteDatabase db = helper.getWritableDatabase();

        for (int i = 0; i < labels.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DBUtil.userId, userid);
            values.put(DBUtil.label, labels.get(i));
            long index = 0;
            index = db.insert(DBUtil.Propensity_TABLE_NAME, null, values);
            if (index == 0) {
                flag = false;
                break;
            }
        }
        close(db);
        return flag;

    }

    public synchronized boolean deletelabels(int userid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = DBUtil.userId + "=?";
        String[] whereargs = {String.valueOf(userid)};
        db.delete(DBUtil.Propensity_TABLE_NAME, whereClause, whereargs);
        return true;
    }

    public synchronized List<String> selelabels(int userid) {
        List<String> mylabels = new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[] = {DBUtil.label};
        String selection = DBUtil.userId + "=" + userid;
        Cursor cur = db.query(DBUtil.Propensity_TABLE_NAME, columns, selection, null, null, null, null, null);
        if (cur.getCount() != 0) {
            cur.moveToFirst();
            do {
                String label = cur.getString(cur.getColumnIndexOrThrow(DBUtil.label));
                mylabels.add(label);
            } while (cur.moveToNext());
        }
        close(db, cur);
        return mylabels;
    }
}
