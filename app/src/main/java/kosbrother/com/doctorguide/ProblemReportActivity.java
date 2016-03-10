package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.model.ProblemReportModel;
import kosbrother.com.doctorguide.presenter.ProblemReportPresenter;
import kosbrother.com.doctorguide.view.ProblemReportView;
import kosbrother.com.doctorguide.viewmodel.ProblemReportViewModel;

public class ProblemReportActivity extends BaseActivity implements ProblemReportView {

    private ProblemReportPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProblemReportModel model = new ProblemReportModel(getViewModel());
        presenter = new ProblemReportPresenter(this, model);
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_problem_report);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("問題回報");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setReportType(String reportTypeText) {
        ((TextView) findViewById(R.id.report_type)).setText(reportTypeText);
    }

    @Override
    public void setReportPage(String reportPageText) {
        ((TextView) findViewById(R.id.report_page)).setText(reportPageText);
    }

    @Override
    public void showNoContentSnackBar() {
        Util.showSnackBar(findViewById(R.id.submit), "請填寫問題內容");
    }

    @Override
    public void showSubmitSuccessDialog() {
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

    @Override
    public void showProgressDialog() {
        progressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    public void onReportSubmitButtonClick(View view) {
        String content = ((EditText) findViewById(R.id.report_content)).getText().toString();
        presenter.onSubmitClick(content);
    }

    private ProblemReportViewModel getViewModel() {
        ProblemReportViewModel viewModel = new ProblemReportViewModel();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                viewModel.setDivisionName(extras.getString(ExtraKey.DIVISION_NAME));
                viewModel.setHospitalName(extras.getString(ExtraKey.HOSPITAL_NAME));
                viewModel.setDoctorName(extras.getString(ExtraKey.DOCTOR_NAME));
                viewModel.setReportType(extras.getString(ExtraKey.REPORT_TYPE));
                viewModel.setDoctorId(extras.getInt(ExtraKey.DOCTOR_ID));
                viewModel.setHospitalId(extras.getInt(ExtraKey.HOSPITAL_ID));
                viewModel.setDivisionId(extras.getInt(ExtraKey.DIVISION_ID));
            }
        }
        return viewModel;
    }
}
