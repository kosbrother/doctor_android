package kosbrother.com.doctorguide.fragments.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyHospital> ITEMS = new ArrayList<DummyHospital>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyHospital> ITEM_MAP = new HashMap<String, DummyHospital>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyHospital item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static DummyHospital createDummyItem(int position) {
        return new DummyHospital(String.valueOf(position), "台北市立聯合醫院 ", "教學醫院", "台北市大同醫鄭州路45號", "1.6km", 9, 12, 4.2);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyHospital {
        public final String id;
        public final String name;
        public final String grade;
        public final String address;
        public final String distance;
        public final int recommend;
        public final int comment;
        public final double score;


        public DummyHospital(String id, String name, String grade, String address, String distance, int recommend, int comment, double score) {
            this.id = id;
            this.name = name;
            this.grade = grade;
            this.address = address;
            this.distance = distance;
            this.recommend = recommend;
            this.comment = comment;
            this.score = score;
        }
    }
}
