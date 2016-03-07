package kosbrother.com.doctorguide.viewmodel;

import android.content.Intent;
import android.os.Bundle;

import kosbrother.com.doctorguide.Util.ExtraKey;

public class DivisionFabViewModel {

    private int divisionId = 0;
    private String divisionName = "";
    private int hospitalId = 0;
    private String hospitalName = "";

    public DivisionFabViewModel(Intent intent) {
        if (intent == null) {
            return;
        }
        Bundle extras = intent.getExtras();
        if (extras != null) {
            divisionId = extras.getInt(ExtraKey.DIVISION_ID);
            divisionName = extras.getString(ExtraKey.DIVISION_NAME);
            hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
            hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
        }
    }

    public int getDivisionId() {
        return divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }
}
