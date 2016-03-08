package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import kosbrother.com.doctorguide.Util.ExtraKey;

public class DoctorActivityViewModel {
    private int doctorId = 0;
    private String doctorName = "";
    private int hospitalId = 0;
    private String hospitalName = "";

    public DoctorActivityViewModel(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            doctorId = extras.getInt(ExtraKey.DOCTOR_ID);
            doctorName = extras.getString(ExtraKey.DOCTOR_NAME);
            hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
            hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
        }
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }
}
