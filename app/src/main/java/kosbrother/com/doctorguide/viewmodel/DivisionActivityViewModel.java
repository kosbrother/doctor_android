package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import kosbrother.com.doctorguide.Util.ExtraKey;

public class DivisionActivityViewModel {
    private int divisionId = 0;
    private String divisionName = "";
    private int hospitalId = 0;
    private String hospitalGrade = "";
    private String hospitalName = "";

    public DivisionActivityViewModel(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            divisionId = extras.getInt(ExtraKey.DIVISION_ID);
            divisionName = extras.getString(ExtraKey.DIVISION_NAME);
            hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
            hospitalGrade = extras.getString(ExtraKey.HOSPITAL_GRADE);
            hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
        }
    }

    public int getDivisionId() {
        return divisionId;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public String getHospitalGrade() {
        return hospitalGrade;
    }

    public String getHospitalName() {
        return hospitalName;
    }
}
