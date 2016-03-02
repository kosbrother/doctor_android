package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.adddoctor.AddDoctorSubmitEvent;
import kosbrother.com.doctorguide.model.AddDoctorModelImpl;
import kosbrother.com.doctorguide.presenter.AddDoctorPresenter;
import kosbrother.com.doctorguide.view.AddDoctorView;
import kosbrother.com.doctorguide.viewmodel.AddDoctorViewModelImpl;

public class AddDoctorActivity extends AppCompatActivity implements AddDoctorView {

    private AddDoctorPresenter presenter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddDoctorViewModelImpl viewModel = new AddDoctorViewModelImpl(getIntent());
        presenter = new AddDoctorPresenter(this, new AddDoctorModelImpl(viewModel));
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_doctor);
    }

    @Override
    public void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle(getString(R.string.add_doctor_action_bar_title));
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setDivisionText(String divisionName) {
        ((EditText) findViewById(R.id.division_name)).setText(divisionName);
    }

    @Override
    public void setHospitalText(String hospitalName) {
        ((EditText) findViewById(R.id.hospial_name)).setText(hospitalName);
    }

    @Override
    public void sendAddDoctorSubmitEvent(String label) {
        GAManager.sendEvent(new AddDoctorSubmitEvent(label));
    }

    @Override
    public void showNoDoctorSnackBar() {
        showSnackBar("請填寫醫師名");
    }

    @Override
    public void showNoHospitalSnackBar() {
        showSnackBar("請填寫醫院名");
    }

    private void showSnackBar(String content) {
        Util.showSnackBar(findViewById(R.id.submit), content);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showSubmitSuccessDialog() {
        new AlertDialog.Builder(this)
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

    public void onSubmitButtonClick(View v) {
        presenter.onSubmitClick(getEditTextString(R.id.doctor_name), getEditTextString(R.id.hospial_name));
    }

    @Override
    public HashMap<String, String> getSubmitData() {
        HashMap<String, String> submitData = new HashMap<>();
        submitData.put("name", getEditTextString(R.id.doctor_name));
        submitData.put("hospital_name", getEditTextString(R.id.hospial_name));
        submitData.put("division_name", getEditTextString(R.id.division_name));
        submitData.put("spe", getEditTextString(R.id.doctor_spe));
        submitData.put("exp", getEditTextString(R.id.doctor_exp));
        return submitData;
    }

    private String getEditTextString(int editTextId) {
        return ((EditText) findViewById(editTextId)).getText().toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.onHomeItemSelected();
        }
        return true;
    }
}
