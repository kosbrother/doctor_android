package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.BuildConfig;
import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.task.CreateUserTask.CreateUserListener;

public class MainModel {
    public void requestCreateUser(CreateUserListener listener) {
        new CreateUserTask(listener).execute(GoogleSignInManager.getInstance().getUser());
    }

    public String getUserName() {
        return GoogleSignInManager.getInstance().getName();
    }

    public String getUserEmail() {
        GoogleSignInManager googleSignInManager = GoogleSignInManager.getInstance();
        return googleSignInManager.isSignIn() ? googleSignInManager.getEmail() : null;
    }

    public String getVersionName() {
        return "APP版本 " + BuildConfig.VERSION_NAME;
    }
}
