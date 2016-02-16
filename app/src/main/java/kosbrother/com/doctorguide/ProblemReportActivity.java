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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class ProblemReportActivity extends AppCompatActivity {

    private ActionBar actionbar;
    private String divisionName;
    private String hospitalName;
    private String reportType;
    private String version;
    private TextView reportText;
    private String reportString;
    private String doctorName;
    private TextView reportTypeText;
    private int doctorId;
    private int hospitalId;
    private int divisionId;
    private HashMap<String,String> submitParams = new HashMap<String,String>();
    private EditText reportContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            divisionName = extras.getString("DIVISION_NAME");
            hospitalName = extras.getString("HOSPITAL_NAME");
            doctorName = extras.getString("DOCTOR_NAME");
            reportType = extras.getString("REPORT_TYPE");
            doctorId = extras.getInt("DOCTOR_ID");
            hospitalId = extras.getInt("HOSPITAL_ID");
            divisionId = extras.getInt("DIVISION_ID");
        }

        actionbar = getSupportActionBar();
        actionbar.setTitle("問題回報");
        actionbar.setDisplayHomeAsUpEnabled(true);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;

        setViews();
        setSubmit();
    }

    private void setSubmit() {
        Button submit = (Button)findViewById(R.id.submit);
        reportContent = (EditText)findViewById(R.id.report_content);

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reportContent.getText().toString().equals("")){
                    Util.showSnackBar(v, "請填寫問題內容");
                }else {
                    setSubmitParams();
                    new PostProblemTask().execute();
                }
            }
        });
    }

    private void setSubmitParams() {
        if(doctorId != 0)
            submitParams.put("doctor_id",doctorId+"");
        if(hospitalId != 0)
            submitParams.put("hospital_id",hospitalId+"");
        if(divisionId != 0)
            submitParams.put("division_id",divisionId+"");
        submitParams.put("content",reportContent.getText().toString());
    }

    private class PostProblemTask extends AsyncTask {

        private ProgressDialog mProgressDialog;
        private Boolean isSuccess;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(ProblemReportActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            isSuccess = DoctorGuideApi.postProblem(submitParams);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if(isSuccess){
                new AlertDialog.Builder(ProblemReportActivity.this)
                        .setTitle("成功提交")
                        .setMessage("我們會儘速改進，謝謝你的提報！")
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

    private void setViews() {
        reportTypeText = (TextView)findViewById(R.id.report_type);
        reportTypeText.setText(reportType);

        reportText = (TextView)findViewById(R.id.report_string);
        reportString = "";
        reportString = hospitalName;
        if(doctorName != null){
            reportString += " | " + doctorName + " 醫師";
        }else if(divisionName != null)
            reportString += " | " + divisionName;
        reportText.setText(reportString);
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
