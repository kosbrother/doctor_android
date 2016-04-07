package kosbrother.com.doctorguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import kosbrother.com.doctorguide.Util.ExtraKey;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onNewIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String action = intent.getAction();
        String data = intent.getDataString();
        Intent startIntent;
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            if (data.contains("hospitals") && data.contains("doctors")) {
                startIntent = new Intent(this, DoctorActivity.class);
                startIntent.putExtra(ExtraKey.HOSPITAL_ID, getHospitalIdFromDoctors(data));
                startIntent.putExtra(ExtraKey.HOSPITAL_NAME, getHospitalNameFromDoctors(data));
                startIntent.putExtra(ExtraKey.DOCTOR_ID, getDoctorIdFromDoctors(data));
                startIntent.putExtra(ExtraKey.DOCTOR_NAME, getDoctorNameFromDoctors(data));
            } else if (data.contains("hospitals")) {
                startIntent = new Intent(this, HospitalActivity.class);
                startIntent.putExtra(ExtraKey.HOSPITAL_ID, getHospitalIdFromHospitals(data));
                startIntent.putExtra(ExtraKey.HOSPITAL_NAME, getHospitalNameFromHospitals(data));
                startIntent.putExtra(ExtraKey.HOSPITAL_GRADE, getHospitalGradeFromHospitals(data));
            } else {
                startIntent = new Intent(this, MainActivity.class);
            }
            startActivity(startIntent);
        }
        finish();
    }

    private int getDoctorIdFromDoctors(String data) {
        return Integer.parseInt(getLastDataSplit(data)[0]);
    }

    private String getDoctorNameFromDoctors(String data) {
        return Uri.decode(getLastDataSplit(data)[1]);
    }

    private int getHospitalIdFromDoctors(String data) {
        return Integer.parseInt(getHospitalsDataSplit(data)[0]);
    }

    private String getHospitalNameFromDoctors(String data) {
        return Uri.decode(getHospitalsDataSplit(data)[1]);
    }

    private int getHospitalIdFromHospitals(String data) {
        return Integer.parseInt(getLastDataSplit(data)[0]);
    }

    private String getHospitalNameFromHospitals(String data) {
        return Uri.decode(getLastDataSplit(data)[1]);
    }

    private String getHospitalGradeFromHospitals(String data) {
        return Uri.decode(getLastDataSplit(data)[2]);
    }

    @NonNull
    private String[] getLastDataSplit(String data) {
        return data.substring(data.lastIndexOf("/") + 1).split("-");
    }

    @NonNull
    private String[] getHospitalsDataSplit(String data) {
        String endIndexString;
        if (data.contains("divisions")) {
            // data from web
            endIndexString = "/divisions";
        } else {
            // data from app index
            endIndexString = "/doctors";
        }
        return data.substring(data.indexOf("hospitals/") + 10, data.indexOf(endIndexString)).split("-");
    }

}
