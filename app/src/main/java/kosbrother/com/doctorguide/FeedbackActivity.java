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
                final Intent emailIntent2 = new Intent(Intent.ACTION_SEND);
                emailIntent2.setType("plain/text");
                emailIntent2.putExtra(Intent.EXTRA_EMAIL, new String[]{FeedbackActivity.this.getString(R.string.respond_mail_address)});
                emailIntent2.putExtra(Intent.EXTRA_SUBJECT, FeedbackActivity.this.getString(R.string.feedback_title));
                emailIntent2.putExtra(Intent.EXTRA_TEXT,
                        "主旨: " + feedbackTitle.getText().toString() + "\n"
                                + "回饋意見: " + feedbackContent.getText().toString() + "\n\n"
                        + "程式碼版本: " + version
                );
                startActivity(Intent.createChooser(emailIntent2, "Send mail..."));
            }
        });
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
