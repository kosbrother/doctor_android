package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

public class AddCommentViewModelImpl implements AddCommentViewModel {
    private int hospitalId;
    private int divisionId;
    private int doctorId;
    private String hospitalName = "";
    private String user = "";

    public AddCommentViewModelImpl(Intent intent) {
        if (intent == null) {
            return;
        }

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }

        divisionId = extras.getInt("DIVISION_ID");
        doctorId = extras.getInt("DOCTOR_ID");
        hospitalId = extras.getInt("HOSPITAL_ID");
        hospitalName = extras.getString("HOSPITAL_NAME");
        user = extras.getString("USER");
    }

    @Override
    public String getHospitalName() {
        return hospitalName;
    }

    @Override
    public int getHospitalId() {
        return hospitalId;
    }

    @Override
    public int getDoctorId() {
        return doctorId;
    }

    @Override
    public int getDivisionId() {
        return divisionId;
    }

    @Override
    public String getUser() {
        return user;
    }
}
