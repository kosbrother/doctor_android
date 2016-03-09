package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import kosbrother.com.doctorguide.Util.ExtraKey;

public class HospitalActivityViewModel {
    private int hospitalId = 0;
    private String hospitalGrade = "";
    private String hospitalName = "";

    public HospitalActivityViewModel(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
            hospitalGrade = extras.getString(ExtraKey.HOSPITAL_GRADE);
            hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
        }
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getHospitalGrade() {
        return hospitalGrade;
    }

    public String getHospitalName() {
        return hospitalName;
    }
}
