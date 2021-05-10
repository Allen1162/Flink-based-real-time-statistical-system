package scau.Allen_HJL.Test3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class JDBCForUser {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection= ConnectionUtil.getConnection();

//        HashMap<Integer, ItemNameAndType> integerItemNameAndTypeHashMap = new HashMap<>();

        //这里必须设置为false，我们手动批量提交
        connection.setAutoCommit(false);

        //这里需要注意，SQL语句的格式必须是预处理的这种，就是values(?,?,...,?)，否则批处理不起作用
        PreparedStatement statement=connection.prepareStatement("insert into user(user_id, user_name, user_age, user_home,  user_outcome) values(?,?,?,?,?)");

//        System.out.println("开始插入数据");
//        Long startTime = System.currentTimeMillis();
        try{
            while(true){
                //一次性插入20条，间隔5s
                Random random = new Random();
                int userId = 100;
                for (int i = 0; i <20 ; i++) {
                    statement.setInt(1, userId++);
                    statement.setString(2, String.valueOf(userId) + "test");
                    int userAge = random.nextInt(50)+1;
                    statement.setInt(3, userAge);
                    int homeIndex = random.nextInt(109);
                    System.out.println(homeIndex);
                    statement.setString(4, UserUtil.city[homeIndex]);
                    int userOutcome = 0;
                    if(UserUtil.hotMap.get(homeIndex) != null){
                        userOutcome = 2*(random.nextInt(100)+1);
                    }else{
                        userOutcome = random.nextInt(100)+1;
                    }
                    statement.setInt(5, userOutcome);
//                    statement.setString(4,itemPrice);
//                    statement.setString(5,itemType);
                    //将要执行的SQL语句先添加进去，不执行
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
                System.out.println("20条数据插入成功！！");
                Thread.sleep(5000);
            }
        }catch (Exception e){
            System.out.println(e);
            System.out.println("取消了，断开连接");
            statement.close();
            ConnectionUtil.close(connection);
        }

//        Long endTime = System.currentTimeMillis();
//        System.out.println("插入完毕,用时：" + (endTime - startTime));
    }
}
