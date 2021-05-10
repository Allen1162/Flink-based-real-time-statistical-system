package scau.Allen_HJL.Test3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;


public class JDBCForItem {

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection connection= ConnectionUtil.getConnection();

//        HashMap<Integer, ItemNameAndType> integerItemNameAndTypeHashMap = new HashMap<>();

        //这里必须设置为false，我们手动批量提交
        connection.setAutoCommit(false);

        //这里需要注意，SQL语句的格式必须是预处理的这种，就是values(?,?,...,?)，否则批处理不起作用
        PreparedStatement statement=connection.prepareStatement("insert into item(item_id, item_name, item_num, item_price,  item_type) values(?,?,?,?,?)");

//        System.out.println("开始插入数据");
//        Long startTime = System.currentTimeMillis();
        try{
            while(true){
                //一次性插入10条，间隔5s
                for (int i = 0; i <10 ; i++) {
                    Random random = new Random();
                    int itemId = random.nextInt(14)+11;
                    String itemName = ItemUtil.integerItemNameAndTypeHashMap.get(itemId).getItemName();
                    String itemType = ItemUtil.integerItemNameAndTypeHashMap.get(itemId).getItemType();
                    statement.setInt(1,itemId);
                    statement.setString(2,itemName);
                    int itemNum = random.nextInt(5)+1;
                    statement.setInt(3,itemNum);
                    int itemPrice = random.nextInt(100)+1;
                    statement.setInt(4,itemPrice);
                    statement.setString(5,itemType);
                    //将要执行的SQL语句先添加进去，不执行
                    statement.addBatch();
                }
                //100W条SQL语句已经添加完成，执行这100W条命令并提交
                statement.executeBatch();
                connection.commit();
                System.out.println("10条数据插入成功！！");
                Thread.sleep(5000);
            }
        }catch (Exception e){
            System.out.println("取消了，断开连接");
            statement.close();
            ConnectionUtil.close(connection);
        }


//        Long endTime = System.currentTimeMillis();
//        System.out.println("插入完毕,用时：" + (endTime - startTime));
    }

}
