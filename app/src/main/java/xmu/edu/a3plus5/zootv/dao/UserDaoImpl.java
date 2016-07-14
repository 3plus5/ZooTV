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
import xmu.edu.a3plus5.zootv.entity.Propensity;
import xmu.edu.a3plus5.zootv.entity.Room;
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
    public User addUserbyUser(User user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.userPic, user.getUserPic());
        values.put(DBUtil.userName, user.getUserName());
        values.put(DBUtil.signCount, user.getSignCount());
        values.put(DBUtil.logCount, user.getLogCount());
        long i=0;
        i = db.insert(DBUtil.User_TABLE_NAME, null, values);
        User myuser=selectuser(user);
        return myuser;
    }

    public User selectuser(User user){
        User myuser=null;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.userId,DBUtil.userName,DBUtil.userPic,DBUtil.signCount,DBUtil.logCount};
        String selection=DBUtil.userName+"="+user.getUserName()+" and "+DBUtil.userPic+"="+user.getUserPic();
        Cursor cur=db.query(DBUtil.User_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0){
            cur.moveToFirst();
            do{
                int userid=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.userId));
                int signcount=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.signCount));
                int logcount=cur.getInt(cur.getColumnIndexOrThrow(DBUtil.logCount));
                myuser =new User(userid,user.getUserPic(),user.getUserName(),signcount,logcount);
            }while(cur.moveToNext());
        }
        close(db,cur);
        return myuser;
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

    public boolean ifhaveinterest(int userid, int rid){
        boolean flag=false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.interestId};
        String selection=DBUtil.userId+"="+userid+" and "+DBUtil.rid+"="+rid;
        Cursor cur=db.query(DBUtil.Interest_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0)
            flag=true;
        close(db,cur);
        return flag;
    }

    public void deleteinterest(int userid, int rid){
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = DBUtil.userId+"=? and "+DBUtil.rid+"=?";
        String[] whereargs={String.valueOf(userid),String.valueOf(rid)};
        db.delete(DBUtil.Interest_TABLE_NAME,whereClause,whereargs);
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

    public boolean ifhavehistory(int userid, int rid){
        boolean flag=false;
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.historyId};
        String selection=DBUtil.userId+"="+userid+" and "+DBUtil.rid+"="+rid;
        Cursor cur=db.query(DBUtil.History_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0)
            flag=true;
        close(db,cur);
        return flag;
    }

    public void deletehistory(int userid, int rid){
        SQLiteDatabase db = helper.getWritableDatabase();
        String whereClause = DBUtil.userId+"=? and "+DBUtil.rid+"=?";
        String[] whereargs={String.valueOf(userid),String.valueOf(rid)};
        db.delete(DBUtil.History_TABLE_NAME,whereClause,whereargs);
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

    public boolean addlabel(int userid,String[] labels){
        boolean flag=true;
        SQLiteDatabase db = helper.getWritableDatabase();

        for(int i=0;i<labels.length;i++)
        {
            ContentValues values = new ContentValues();
            values.put(DBUtil.userId , userid);
            values.put(DBUtil.label , labels[i]);
            long index=0;
            index = db.insert(DBUtil.Propensity_TABLE_NAME, null, values);
            if(index==0){
                flag=false;
                break;
            }
        }
        close(db);
        return flag;

    }

    public List<String> selelabels(int userid){
        List<String> mylabels=new ArrayList<String>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.label};
        String selection=DBUtil.userId+"="+userid;
        Cursor cur=db.query(DBUtil.Propensity_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0){
            cur.moveToFirst();
            do{
                String label=cur.getString(cur.getColumnIndexOrThrow(DBUtil.label));
                mylabels.add(label);
            }while(cur.moveToNext());
        }
        close(db,cur);
        return mylabels;
    }

    public boolean addRoom(Room room){
        boolean flag=false;
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtil.platform , room.getPlatform());
        values.put(DBUtil.roomId , room.getRoomId());
        long i=0;
        i = db.insert(DBUtil.Room_TABLE_NAME, null, values);
        if(i!=0)
            flag=true;
        close(db);
        return flag;
    }

    public boolean ifhaveRoom(Room room){
        boolean flag=false;
        List<Room> myrooms=new ArrayList<Room>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String columns[]={DBUtil.rid};
        String selection=DBUtil.platform +"= '"+room.getPlatform()+"' and "+DBUtil.roomId+"='"+room.getRoomId()+"'";
        Cursor cur=db.query(DBUtil.Room_TABLE_NAME,columns,selection,null,null,null,null,null);
        if(cur.getCount()!=0)
            flag=true;
        close(db,cur);
        return flag;
    }
}
