package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.model.FeedbackModel;
import kosbrother.com.doctorguide.presenter.FeedbackPresenter;
import kosbrother.com.doctorguide.view.FeedbackView;

public class FeedbackActivity extends BaseActivity implements FeedbackView {

    private FeedbackPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FeedbackPresenter(this, new FeedbackModel());
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("意見回饋");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showNoTitleSnackBar() {
        Util.showSnackBar(findViewById(R.id.submit), "請填寫主旨");
    }

    @Override
    public void showNoContentSnackBar() {
        Util.showSnackBar(findViewById(R.id.submit), "請填寫改進建議");
    }

    @Override
    public void showPostFeedbackSuccessDialog() {
        new AlertDialog.Builder(FeedbackActivity.this)
                .setTitle("成功提交")
                .setMessage("我們會儘速改進，謝謝你的意見！")
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

    public void onFeedbackSubmitButtonClick(View view) {
        String title = ((EditText) findViewById(R.id.feedback_title)).getText().toString();
        String content = ((EditText) findViewById(R.id.feedback_content)).getText().toString();
        presenter.onSubmitClick(title, content);
    }
}
