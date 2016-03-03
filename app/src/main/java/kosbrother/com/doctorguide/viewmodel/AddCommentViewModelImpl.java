package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import kosbrother.com.doctorguide.Util.ExtraKey;

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

        divisionId = extras.getInt(ExtraKey.DIVISION_ID);
        doctorId = extras.getInt(ExtraKey.DOCTOR_ID);
        hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
        hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
        user = extras.getString(ExtraKey.USER);
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
