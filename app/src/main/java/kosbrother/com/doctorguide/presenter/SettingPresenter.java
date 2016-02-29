package kosbrother.com.doctorguide.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import kosbrother.com.doctorguide.google_signin.GoogleSigninInteractor;
import kosbrother.com.doctorguide.view.SettingView;

public class SettingPresenter implements ResultCallback<Status> {
    private SettingView view;
    private GoogleSigninInteractor googleSigninInteractor;

    public SettingPresenter(SettingView view, GoogleSigninInteractor googleSigninInteractor) {
        this.view = view;
        this.googleSigninInteractor = googleSigninInteractor;
    }

    public void onCreate() {
        view.setContentView();
        view.initActionBar();
    }

    public void onHomeItemSelected() {
        view.finish();
    }

    public void onAboutUsClick() {
        view.startAboutUsActivity();
    }

    public void onSignOutClick() {
        googleSigninInteractor.signOut(this);
    }

    @Override
    public void onResult(@NonNull Status status) {
        view.showSignOutSuccessSnackBar();
    }
}
