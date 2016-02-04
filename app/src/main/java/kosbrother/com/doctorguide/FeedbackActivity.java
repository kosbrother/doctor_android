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

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class FeedbackActivity extends AppCompatActivity {

    private ActionBar actionbar;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        actionbar = getSupportActionBar();
        actionbar.setTitle("意見回饋");
        actionbar.setDisplayHomeAsUpEnabled(true);

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;
        setSubmit();
    }

    private void setSubmit() {
        Button submit = (Button)findViewById(R.id.submit);
        final EditText feedbackTitle = (EditText)findViewById(R.id.feedback_title);
        final EditText feedbackContent = (EditText)findViewById(R.id.feedback_content);

        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedbackTitle.getText().toString().equals("")){
                    Util.showSnackBar(v, "請填寫主旨");
                }else if(feedbackContent.getText().toString().equals("")){
                    Util.showSnackBar(v, "請填寫改進建議");
                }else{
                    new PostCommentTask().execute();
                }
            }
        });
    }

    private class PostCommentTask extends AsyncTask {

        private ProgressDialog mProgressDialog;
        private Boolean isSuccess;
        private String subject;
        private String content;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(FeedbackActivity.this);
            final EditText feedbackTitle = (EditText)findViewById(R.id.feedback_title);
            final EditText feedbackContent = (EditText)findViewById(R.id.feedback_content);
            subject = feedbackTitle.getText().toString();
            content = feedbackContent.getText().toString();
        }
        @Override
        protected Object doInBackground(Object... params) {
            isSuccess = DoctorGuideApi.postFeedback(subject,content);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if(isSuccess){
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
