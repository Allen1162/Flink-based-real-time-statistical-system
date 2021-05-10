package scau.Allen_HJL.Test3;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {

    private Integer itemId;
    private String itemName;
    private Integer itemNum;
    private Integer itemPrice;
    private String itemType;
    private Long timeStamp; //时间戳

}
