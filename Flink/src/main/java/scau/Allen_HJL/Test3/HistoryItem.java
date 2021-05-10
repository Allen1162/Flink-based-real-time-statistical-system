package scau.Allen_HJL.Test3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryItem {

    private String windowEnd;
    private ArrayList<ItemTypeAndSumPrice> historyItemTypeAndSumPriceArrayList;

}
