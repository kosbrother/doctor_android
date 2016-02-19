package kosbrother.com.doctorguide.google_analytics.event.division;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class DivisionClickHospitalTextEvent implements GAEvent {
    private String label;

    public DivisionClickHospitalTextEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.DIVISION;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_HOSPITAL_TEXT;
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
