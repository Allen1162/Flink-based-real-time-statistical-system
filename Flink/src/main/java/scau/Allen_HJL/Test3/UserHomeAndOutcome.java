package scau.Allen_HJL.Test3;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserHomeAndOutcome implements Comparable<UserHomeAndOutcome> {

    private String userHome;
    private Long userOutcome;
    private Long windowEnd;

    //按支出降序排序
    @Override
    public int compareTo(UserHomeAndOutcome o) {
        return o.getUserOutcome().compareTo(this.getUserOutcome());
    }
}
