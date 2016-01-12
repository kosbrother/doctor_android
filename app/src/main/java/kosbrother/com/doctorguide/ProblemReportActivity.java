package kosbrother.com.doctorguide;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        final EditText reportContent = (EditText)findViewById(R.id.report_content);

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent2 = new Intent(Intent.ACTION_SEND);


                emailIntent2.setType("plain/text");
                emailIntent2.putExtra(Intent.EXTRA_EMAIL, new String[]{ProblemReportActivity.this.getString(R.string.respond_mail_address)});
                emailIntent2.putExtra(Intent.EXTRA_SUBJECT, ProblemReportActivity.this.getString(R.string.report_title));
                emailIntent2.putExtra(Intent.EXTRA_TEXT,
                        "勘誤位置: " + reportString + "\n\n"
                                + "勘誤內容: " + reportContent.getText().toString() + "\n\n"
                                + "程式碼版本: " + version
                );
                startActivity(Intent.createChooser(emailIntent2, "Send mail..."));
            }
        });
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
