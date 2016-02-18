package kosbrother.com.doctorguide.google_analytics.event.hospitaldoctor;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class HospitalDoctorClickHospitalListEvent implements GAEvent {
    private String label;

    public HospitalDoctorClickHospitalListEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.HOSPITAL_DOCTOR;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_HOSPITAL_LIST;
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
