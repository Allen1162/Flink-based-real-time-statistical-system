package scau.Allen_HJL.Test3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    //数据库地址
    private static String URL="jdbc:mysql://192.168.37.129:1162/DB?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    //取得驱动程序
    private static String DRIVER="com.mysql.jdbc.Driver";
    //取得用户
    private static String USER="root";
    //登录密码
    private static String PASSWORD="123456";
    //静态代码块加载驱动类信息
    static {
        try {
            Class.forName(DRIVER);//将"com.mysql.jdbc.Driver"类的Class类对象加载到运行时内存中
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //定义获取Connection对象的方法
    public static Connection getConnection() {
        //定义Connection对象
        Connection conn = null;
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
    //关闭连接操作
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

