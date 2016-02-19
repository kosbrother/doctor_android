package kosbrother.com.doctorguide.google_analytics.event.main;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class MainClickDivisionInfoEvent implements GAEvent {
    private String label;

    public MainClickDivisionInfoEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.MAIN;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_DIVISION_INFO;
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
