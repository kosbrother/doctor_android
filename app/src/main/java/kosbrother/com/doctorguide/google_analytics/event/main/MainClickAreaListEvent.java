package kosbrother.com.doctorguide.google_analytics.event.main;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class MainClickAreaListEvent implements GAEvent {
    private final String label;

    public MainClickAreaListEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.MAIN;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_AREA_LIST;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public long getValue() {
        return 0;
    }
}
