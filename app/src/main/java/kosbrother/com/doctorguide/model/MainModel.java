package kosbrother.com.doctorguide.model;

import android.net.Uri;

import kosbrother.com.doctorguide.BuildConfig;
import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.task.CreateUserTask.CreateUserListener;

public class MainModel {

    public static final Uri APP_URI = Uri.parse("android-app://kosbrother.com.doctorguide/http/doctorguide.tw");
    public static final Uri WEB_URL = Uri.parse("http://doctorguide.tw/");

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
