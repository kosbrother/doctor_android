package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.adddoctor.AddDoctorSubmitEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;

import static kosbrother.com.doctorguide.Util.Util.showSnackBar;

public class AddDoctorActivity extends AppCompatActivity {

    private ActionBar actionbar;
    private String divisionName;
    private String hospitalName;
    private EditText doctorEdit;
    private EditText hospitalEdit;
    private EditText divisionEdit;
    private EditText speEdit;
    private EditText expEdit;
    private String version;
    private int hospitalId;
    private int divisionId;
    private HashMap<String, String> submitParams = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            divisionName = extras.getString("DIVISION_NAME");
            hospitalName = extras.getString("HOSPITAL_NAME");
            hospitalId = extras.getInt("HOSPITAL_ID");
            divisionId = extras.getInt("DIVISION_ID");
        }

        actionbar = getSupportActionBar();
        actionbar.setTitle("新增醫師");
        actionbar.setDisplayHomeAsUpEnabled(true);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;

        findAndSetViews();
    }

    private void findAndSetViews() {
        doctorEdit = (EditText) findViewById(R.id.doctor_name);
        hospitalEdit = (EditText) findViewById(R.id.hospial_name);
        divisionEdit = (EditText) findViewById(R.id.division_name);
        speEdit = (EditText) findViewById(R.id.doctor_spe);
        expEdit = (EditText) findViewById(R.id.doctor_exp);

        if (divisionName != null)
            divisionEdit.setText(divisionName);
        if (hospitalName != null)
            hospitalEdit.setText(hospitalName);
    }

    public void onSubmit(View v) {
        if (doctorEdit.getText().toString().equals("")) {
            GAManager.sendEvent(new AddDoctorSubmitEvent(GALabel.NO_DOCTOR_NAME));

            showSnackBar(v, "請填寫醫師名");
        } else if (hospitalEdit.getText().toString().equals("")) {
            GAManager.sendEvent(new AddDoctorSubmitEvent(GALabel.NO_HOSPITAL_NAME));

            showSnackBar(v, "請填寫醫院名");
        } else {
            GAManager.sendEvent(new AddDoctorSubmitEvent(GALabel.DATA_FILLED));

            setSubmitParams();
            new PostAddDoctorTask().execute();
        }
    }

    private void setSubmitParams() {
        if (hospitalId != 0)
            submitParams.put("hospital_id", hospitalId + "");
        if (divisionId != 0)
            submitParams.put("division_id", divisionId + "");
        submitParams.put("name", doctorEdit.getText().toString());
        submitParams.put("hospital_name", hospitalEdit.getText().toString());
        submitParams.put("division_name", divisionEdit.getText().toString());
        submitParams.put("spe", speEdit.getText().toString());
        submitParams.put("exp", expEdit.getText().toString());
    }

    private class PostAddDoctorTask extends AsyncTask {

        private ProgressDialog mProgressDialog;
        private Boolean isSuccess;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(AddDoctorActivity.this);
        }

        @Override
        protected Object doInBackground(Object... params) {
            isSuccess = DoctorGuideApi.postAddDoctor(submitParams);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (isSuccess) {
                new AlertDialog.Builder(AddDoctorActivity.this)
                        .setTitle("成功提交")
                        .setMessage("我們審核醫師資料後，就會加入該醫師，謝謝！")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
            }

        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
