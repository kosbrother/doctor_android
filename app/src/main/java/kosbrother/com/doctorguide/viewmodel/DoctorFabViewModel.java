package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import kosbrother.com.doctorguide.Util.ExtraKey;

public class DoctorFabViewModel {
    private String hospitalName = "";
    private String doctorName = "";
    private int doctorId = 0;
    private int hospitalId = 0;

    public DoctorFabViewModel(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            doctorId = extras.getInt(ExtraKey.DOCTOR_ID);
            doctorName = extras.getString(ExtraKey.DOCTOR_NAME);
            hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
            hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
        }
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public int getHospitalId() {
        return hospitalId;
    }
}
