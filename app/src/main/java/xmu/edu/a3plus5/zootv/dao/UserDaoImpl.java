package xmu.edu.a3plus5.zootv.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import xmu.edu.a3plus5.zootv.db.MySQLiteOpenHelper;
import xmu.edu.a3plus5.zootv.entity.History;
import xmu.edu.a3plus5.zootv.entity.Interest;
import xmu.edu.a3plus5.zootv.entity.User;
import xmu.edu.a3plus5.zootv.util.DBUtil;


public class UserDaoImpl implements  UserDao{
    private MySQLiteOpenHelper helper;
    private static UserDao userdao;

    public UserDaoImpl(Context context){
        helper=new MySQLiteOpenHelper(context);
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
    public boolean addUserbyItem(String userPic,String userName) {
        boolean flag=false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userPic, userPic);
        values.put(DBUtil.userName, userName);
        values.put(DBUtil.signCount, 0);
        values.put(DBUtil.logCount, 0);
        long i=0;
        i = db.insert(DBUtil.User_TABLE_NAME, null, values);
        if(i!=0)
            flag=true;
        close(db);
        return flag;
    }

    @Override
    public boolean addUserbyUser(User user){
        boolean flag=false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userPic, user.getUserPic());
        values.put(DBUtil.userName, user.getUserName());
        values.put(DBUtil.signCount, user.getSignCount());
        values.put(DBUtil.logCount, user.getLogCount());
        long i=0;
        i = db.insert(DBUtil.User_TABLE_NAME, null, values);
        if(i!=0)
            flag=true;
        close(db);
        return flag;
    }

    @Override
    public boolean addinterest(int userid, int rid){
        boolean flag=false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userId , userid);
        values.put(DBUtil.rid , rid);
        long i=0;
        i = db.insert(DBUtil.Interest_TABLE_NAME, null, values);
        if(i!=0)
            flag=true;
        close(db);
        return flag;
    }

    @Override
    public boolean addhistory(int userid,int rid){
        boolean flag=false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userId , userid);
        values.put(DBUtil.rid , rid);
        long i=0;
        i = db.insert(DBUtil.History_TABLE_NAME, null, values);
        if(i!=0)
            flag=true;
        close(db);
        return flag;
    }

    public List<Interest> seleinterest(int userid){
        List<Interest> myinterests=new ArrayList<Interest>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.interestId,DBUtil.rid};
        String selection=DBUtil.userId+"="+userid;
        Cursor cur=db.query(DBUtil.Interest_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0){
            cur.moveToFirst();
            do{
                int interestid=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.interestId));
                int rId=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.rid));
                Interest interest=new Interest(interestid,userid,rId);
                myinterests.add(interest);
            }while(cur.moveToNext());
        }
        close(db,cur);
        return myinterests;
    }

    public List<History> selehistory(int userid){
        List<History> myhistorys=new ArrayList<History>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.historyId,DBUtil.rid};
        String selection=DBUtil.userId+"="+userid;
        Cursor cur=db.query(DBUtil.History_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0){
            cur.moveToFirst();
            do{
                int historyid=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.historyId));
                int rId=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.rid));
                History history=new History(historyid,userid,rId);
                myhistorys.add(history);
            }while(cur.moveToNext());
        }
        close(db,cur);
        return myhistorys;
    }

}
