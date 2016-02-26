package kosbrother.com.doctorguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import kosbrother.com.doctorguide.google_signin.GoogleSigninInteractorImpl;
import kosbrother.com.doctorguide.presenter.SettingPresenter;
import kosbrother.com.doctorguide.view.SettingView;

public class SettingActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, SettingView {

    private SettingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingPresenter(this, new GoogleSigninInteractorImpl(this, this));
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle(getString(R.string.setting_actionbar_title));
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    public void onAboutUsClick(View v) {
        presenter.onAboutUsClick();
    }

    public void onSignOutClick(View v) {
        presenter.onSignOutClick();
    }

    @Override
    public void startAboutUsActivity() {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    @Override
    public void showSignOutSuccessSnackBar() {
        Snackbar.make(findViewById(R.id.setting_rootLayout),
                getString(R.string.setting_sign_out_success),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.onHomeItemSelected();
        }
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
