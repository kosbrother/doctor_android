package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

public class AddDoctorViewModelImpl implements AddDoctorViewModel {
    private String divisionName = "";
    private String hospitalName = "";
    private int hospitalId;
    private int divisionId;

    public AddDoctorViewModelImpl(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }
        divisionName = extras.getString("DIVISION_NAME");
        hospitalName = extras.getString("HOSPITAL_NAME");
        hospitalId = extras.getInt("HOSPITAL_ID");
        divisionId = extras.getInt("DIVISION_ID");
    }

    @Override
    public String getDivisionName() {
        return divisionName;
    }

    @Override
    public String getHospitalName() {
        return hospitalName;
    }

    @Override
    public int getDivisionId() {
        return divisionId;
    }

    @Override
    public int getHospitalId() {
        return hospitalId;
    }
}
