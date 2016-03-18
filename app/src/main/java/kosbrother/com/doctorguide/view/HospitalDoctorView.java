package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;

public interface HospitalDoctorView extends ProgressDialogView {
    void setContentView();

    void setActionBar();

    void setViewPager();

    void showDivisionsDialog(String[] divisionNameArray, Hospital hospital);

    void startDivisionActivity(ArrayList<Division> divisions, Hospital hospital, int position);

    void startDoctorActivity(Doctor doctor);
}
