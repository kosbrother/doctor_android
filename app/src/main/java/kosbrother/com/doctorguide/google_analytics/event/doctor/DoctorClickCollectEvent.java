package kosbrother.com.doctorguide.google_analytics.event.doctor;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class DoctorClickCollectEvent implements GAEvent {
    private String label;

    public DoctorClickCollectEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.DOCTOR;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_COLLECT;
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
