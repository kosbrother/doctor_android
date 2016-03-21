package kosbrother.com.doctorguide.entity;

public class OrderStrings {
    public static final String DISTANCE = "distance";
    public static final String RECOMMEND_NUM = "recommend_num";
    public static final String COMMENT_NUM = "comment_num";
    public static final String AVG = "avg";

    static String[] orderStrings = new String[]{DISTANCE, RECOMMEND_NUM, COMMENT_NUM, AVG};
    static String[] orderStringsName = new String[]{"距離", "推薦數", "評論數", "分數"};

    public static String[] getOrderStringNameArray() {
        return orderStringsName;
    }

    public static String getOrderString(int orderSelection) {
        return orderStrings[orderSelection];
    }

    public static int getStringIndex(String orderString) {
        for (int i = 0; i < orderStrings.length; i++) {
            if (orderString.equals(orderStrings[i])) {
                return i;
            }
        }
        return 0;
    }
}
