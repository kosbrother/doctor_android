package kosbrother.com.doctorguide;

import android.content.Intent;
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
            if (data.contains("hospitals")) {
                startIntent = new Intent(this, HospitalActivity.class);
                startIntent.putExtra(ExtraKey.HOSPITAL_ID, getHospitalId(data));
                startIntent.putExtra(ExtraKey.HOSPITAL_NAME, getHospitalName(data));
                startIntent.putExtra(ExtraKey.HOSPITAL_GRADE, getHospitalGrade(data));
            } else {
                startIntent = new Intent(this, MainActivity.class);
            }
            startActivity(startIntent);
        }
        finish();
    }

    private int getHospitalId(String data) {
        return Integer.parseInt(getSplit(data)[0]);
    }

    private String getHospitalName(String data) {
        return getSplit(data)[1];
    }

    private String getHospitalGrade(String data) {
        return getSplit(data)[2];
    }

    @NonNull
    private String[] getSplit(String data) {
        return data.substring(data.lastIndexOf("/") + 1).split("-");
    }

}
