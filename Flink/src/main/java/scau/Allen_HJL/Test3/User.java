package scau.Allen_HJL.Test3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private int userId;  //用户id
    private String userName; //用户名
    private int userAge; //年龄
    private String userHome; //家乡
    private Long timeStamp; //时间戳
    private long userOutcome; //支出
//    private String userSource; //用户来源

}
