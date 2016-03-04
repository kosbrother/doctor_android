package kosbrother.com.doctorguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import kosbrother.com.doctorguide.presenter.AboutUsPresenter;
import kosbrother.com.doctorguide.view.AboutUsView;

public class AboutUsActivity extends BaseActivity implements AboutUsView {

    private AboutUsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AboutUsPresenter(this);
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_about_us);
    }

    @Override
    public void initContentView() {
        initActionBar();
        initFeedbackButton();
    }

    private void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    private void initFeedbackButton() {
        findViewById(R.id.feedback).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFeedbackButtonClick();
            }
        });
    }

    @Override
    public void startFeedbackActivity() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

}
