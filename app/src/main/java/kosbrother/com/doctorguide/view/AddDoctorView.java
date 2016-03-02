package kosbrother.com.doctorguide.view;

import java.util.HashMap;

public interface AddDoctorView {
    void setContentView();

    void initActionBar();

    void setDivisionText(String divisionName);

    void setHospitalText(String hospitalName);

    void sendAddDoctorSubmitEvent(String label);

    void showNoDoctorSnackBar();

    void showNoHospitalSnackBar();

    void showProgressDialog();

    void hideProgressDialog();

    void showSubmitSuccessDialog();

    HashMap<String,String> getSubmitData();

    void finish();
}
