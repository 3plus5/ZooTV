package xmu.edu.a3plus5.zootv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ashokvarma.bottomnavigation.utils.Utils;

import xmu.edu.a3plus5.zootv.util.DBUtil;


public class MySQLiteOpenHelper extends SQLiteOpenHelper{
    private Context context;
    public MySQLiteOpenHelper(Context context){
        super(context, DBUtil.DATABASE_NAME,null,DBUtil.DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        final String User_table = "CREATE TABLE " + DBUtil.User_TABLE_NAME + " ( "
                + DBUtil.userId + " integer primary key autoincrement,"
                + DBUtil.userPic + " text not null,"
                + DBUtil.userName + " text not null,"
                + DBUtil.signCount + " text not null,"
                + DBUtil.logCount + " text not null);";
        db.execSQL(User_table);

        final String Room_table = "CREATE TABLE " + DBUtil.Room_TABLE_NAME + " ( "
                + DBUtil.rid + " integer primary key autoincrement,"
                + DBUtil.roomId + " text not null,"
                + DBUtil.platform + " text not null);";
        db.execSQL(Room_table);

        final String Interest_table = "CREATE TABLE " + DBUtil.Interest_TABLE_NAME + " ( "
                + DBUtil.interestId + " integer primary key autoincrement,"
                + DBUtil.userId + " integer,"
                + DBUtil.rid + " integer);";
//                +"foreign key(" + DBUtil.userId +") references" + DBUtil.User_TABLE_NAME + "("+ DBUtil.userId +")"
//                +"foreign key(" + DBUtil.roomId +") references" + DBUtil.Room_TABLE_NAME + "("+ DBUtil.roomId +");";
        db.execSQL(Interest_table);

        final String History_table = "CREATE TABLE " + DBUtil.History_TABLE_NAME + " ( "
                + DBUtil.historyId + " integer primary key autoincrement,"
                + DBUtil.userId + " integer,"
                + DBUtil.rid + " integer); ";
//                +"foreign key(" + DBUtil.userId +") references" + DBUtil.User_TABLE_NAME + "("+ DBUtil.userId +")"
//                +"foreign key(" + DBUtil.roomId +") references" + DBUtil.Room_TABLE_NAME + "("+ DBUtil.roomId +");";
        db.execSQL(History_table);

        final String Propensity_table = "CREATE TABLE " + DBUtil.Propensity_TABLE_NAME + " ( "
                + DBUtil.propensityId + " integer primary key autoincrement,"
                + DBUtil.userId + " integer,"
                + DBUtil.label + " text not null); ";
//                +"foreign key(" + DBUtil.userId +") references" + DBUtil.User_TABLE_NAME + "("+ DBUtil.userId +")"
//                +"foreign key(" + DBUtil.roomId +") references" + DBUtil.Room_TABLE_NAME + "("+ DBUtil.roomId +");";
        db.execSQL(Propensity_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgrade = "DORP TABLE IF EXIST " + DBUtil.DATABASE_NAME;
        db.execSQL(upgrade);
        onCreate(db);
    }

}
