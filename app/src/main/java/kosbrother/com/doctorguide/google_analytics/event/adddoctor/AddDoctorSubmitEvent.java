package kosbrother.com.doctorguide.google_analytics.event.adddoctor;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class AddDoctorSubmitEvent implements GAEvent {
    private String label;

    public AddDoctorSubmitEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.ADD_DOCTOR;
    }

    @Override
    public String getAction() {
        return GAAction.SUBMIT_ADD_DOCTOR;
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
