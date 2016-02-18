package kosbrother.com.doctorguide.google_analytics.event.main;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class MainClickAccountEvent implements GAEvent {
    private String label;

    public MainClickAccountEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.MAIN;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_ACCOUNT;
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
