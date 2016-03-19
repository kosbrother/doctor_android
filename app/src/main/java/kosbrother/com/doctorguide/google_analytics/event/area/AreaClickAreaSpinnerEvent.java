package kosbrother.com.doctorguide.google_analytics.event.area;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class AreaClickAreaSpinnerEvent implements GAEvent{
    private final String label;

    public AreaClickAreaSpinnerEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.AREA;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_AREA_SPINNER;
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
