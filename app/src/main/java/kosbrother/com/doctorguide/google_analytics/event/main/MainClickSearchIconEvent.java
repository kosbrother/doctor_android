package kosbrother.com.doctorguide.google_analytics.event.main;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class MainClickSearchIconEvent implements GAEvent {
    @Override
    public String getCategory() {
        return GACategory.MAIN;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_SEARCH_ICON;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public long getValue() {
        return 0;
    }
}
