package scau.Allen_HJL.Test3;

import java.util.HashMap;

public class ItemUtil {
    public static HashMap<Integer, ItemNameAndType> integerItemNameAndTypeHashMap;
    static {
        integerItemNameAndTypeHashMap = new HashMap<>();
        integerItemNameAndTypeHashMap.put(11, new ItemNameAndType("iphone", "手机"));
        integerItemNameAndTypeHashMap.put(12, new ItemNameAndType("华为mate40", "手机"));
        integerItemNameAndTypeHashMap.put(13, new ItemNameAndType("洁柔", "日用品"));
        integerItemNameAndTypeHashMap.put(14, new ItemNameAndType("维达", "日用品"));
        integerItemNameAndTypeHashMap.put(15, new ItemNameAndType("mac", "电脑"));
        integerItemNameAndTypeHashMap.put(16, new ItemNameAndType("外星人", "电脑"));
        integerItemNameAndTypeHashMap.put(17, new ItemNameAndType("iPad", "平板"));
        integerItemNameAndTypeHashMap.put(18, new ItemNameAndType("小米pad", "平板"));
        integerItemNameAndTypeHashMap.put(19, new ItemNameAndType("苹果耳机", "耳机"));
        integerItemNameAndTypeHashMap.put(20, new ItemNameAndType("华为耳机", "耳机"));
        integerItemNameAndTypeHashMap.put(21, new ItemNameAndType("nike跑鞋", "跑鞋"));
        integerItemNameAndTypeHashMap.put(22, new ItemNameAndType("阿迪跑鞋", "跑鞋"));
        integerItemNameAndTypeHashMap.put(23, new ItemNameAndType("可比克", "零食"));
        integerItemNameAndTypeHashMap.put(24, new ItemNameAndType("鼠标", "电脑配件"));
    }
}
