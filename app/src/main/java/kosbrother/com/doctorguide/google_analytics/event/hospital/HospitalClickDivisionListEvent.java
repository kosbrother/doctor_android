package kosbrother.com.doctorguide.google_analytics.event.hospital;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class HospitalClickDivisionListEvent implements GAEvent {
    private String label;

    public HospitalClickDivisionListEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.HOSPITAL;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_DIVISION_LIST;
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
