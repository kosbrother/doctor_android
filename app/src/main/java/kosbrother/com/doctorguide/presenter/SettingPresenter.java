package kosbrother.com.doctorguide.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import kosbrother.com.doctorguide.model.SettingModel;
import kosbrother.com.doctorguide.google_signin.GoogleSigninInteractor;
import kosbrother.com.doctorguide.view.SettingView;

public class SettingPresenter implements ResultCallback<Status> {
    private SettingView view;
    private final SettingModel model;
    private GoogleSigninInteractor googleSigninInteractor;

    public SettingPresenter(SettingView view, SettingModel model, GoogleSigninInteractor googleSigninInteractor) {
        this.view = view;
        this.model = model;
        this.googleSigninInteractor = googleSigninInteractor;
    }

    public void onCreate() {
        view.setContentView();
        view.initActionBar();
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
        model.clearUserData();
    }
}
