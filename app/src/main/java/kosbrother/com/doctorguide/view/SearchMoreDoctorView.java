package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Doctor;

public interface SearchMoreDoctorView extends SearchMoreView {
    void setDoctorsListView(ArrayList<Doctor> doctors);
}
