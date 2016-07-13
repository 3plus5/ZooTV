package xmu.edu.a3plus5.zootv.entity;

/**
 * Created by hd_chen on 2016/7/13.
 */
public class MyUser {
    public static final String SINA="sina";
    public static final String QQ="qq";
    public static final String EMAIL="email";

    public static String userId = "001";
    public static String userName = "visitor";
    public static String userIcon = null;
    public static String loginType="";
    public static String userEmail="";
    public static String easemobId;

    public static String getEasemobId() {
        return easemobId;
    }

    public static void setEasemobId(String easemobId) {
        MyUser.easemobId = easemobId;
    }

    public static void setUserPassword(String userPassword) {
        MyUser.userPassword = userPassword;
    }

    public static void setUserEmail(String userEmail) {
        MyUser.userEmail = userEmail;
    }

    public static String userPassword="";

    public static void setUserId(String userId) {
        MyUser.userId = userId;
    }

    public static void setUserName(String userName) {
        MyUser.userName = userName;
    }

    public static void setUserIcon(String userIcon) {
        MyUser.userIcon = userIcon;
    }

    public static void setLoginType(String loginType) {
        MyUser.loginType = loginType;
    }
}
