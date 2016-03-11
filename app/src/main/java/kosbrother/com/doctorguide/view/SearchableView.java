package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;

public interface SearchableView extends ProgressDialogView{
    void setContentView();

    void setActionBar();

    void setActionBarTitle(String actionBarTitle);

    void setDoctorListView(ArrayList<Doctor> doctors);

    void setHospitalListView(ArrayList<Hospital> hospitals);

    void startSearchMoreHospitalActivity(String queryString);

    void startSearchMoreDoctorActivity(String queryString);
}
