package kosbrother.com.doctorguide.google_analytics.event.addcomment;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class AddCommentClickDoctorSpinnerEvent implements GAEvent {
    private String label;

    public AddCommentClickDoctorSpinnerEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.ADD_COMMENT;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_DOCTOR_SPINNER;
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
