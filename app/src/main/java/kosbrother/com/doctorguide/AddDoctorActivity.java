package kosbrother.com.doctorguide;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            divisionName = extras.getString("DIVISION_NAME");
            hospitalName = extras.getString("HOSPITAL_NAME");
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
        doctorEdit = (EditText)findViewById(R.id.doctor_name);
        hospitalEdit = (EditText)findViewById(R.id.hospial_name);
        divisionEdit = (EditText)findViewById(R.id.division_name);
        speEdit = (EditText)findViewById(R.id.doctor_spe);
        expEdit = (EditText)findViewById(R.id.doctor_exp);

        if(divisionName != null)
            divisionEdit.setText(divisionName);
        if(hospitalName != null)
            hospitalEdit.setText(hospitalName);
    }

    public void onSubmit(View v) {
        if(doctorEdit.getText().toString().equals("")){
            showSnackBar(v,"請填寫醫師名");
        }else if(hospitalEdit.getText().toString().equals("")){
            showSnackBar(v,"請填寫醫院名");
        }else{
            final Intent emailIntent2 = new Intent(Intent.ACTION_SEND);


            emailIntent2.setType("plain/text");
            emailIntent2.putExtra(Intent.EXTRA_EMAIL, new String[]{AddDoctorActivity.this.getString(R.string.respond_mail_address)});
            emailIntent2.putExtra(Intent.EXTRA_SUBJECT, AddDoctorActivity.this.getString(R.string.add_doctor_title));
            emailIntent2.putExtra(Intent.EXTRA_TEXT,
                    "醫師姓名: " + doctorEdit.getText().toString() + "\n\n"
                            + "服務醫院: " + hospitalEdit.getText().toString() + "\n\n"
                            + "服務科別: " + divisionEdit.getText().toString() + "\n\n"
                            + "醫師專長: " + speEdit.getText().toString() + "\n\n"
                            + "醫師經歷: " + expEdit.getText().toString() + "\n\n"
                            + "程式碼版本: " + version
            );
            startActivity(Intent.createChooser(emailIntent2, "Send mail..."));
        }
    }

    public void showSnackBar(View v, String str){
        Snackbar snackbar = Snackbar.make(v, str, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.RED);
        snackbar.show();
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
