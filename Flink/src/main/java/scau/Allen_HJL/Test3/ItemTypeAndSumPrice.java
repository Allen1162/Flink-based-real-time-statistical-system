package scau.Allen_HJL.Test3;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemTypeAndSumPrice {

    private String itemType;
    private Long itemSumNum;
    private Long itemSumPrice;
    private Long windowEnd;

}
