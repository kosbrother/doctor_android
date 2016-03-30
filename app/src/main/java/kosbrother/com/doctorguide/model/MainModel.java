package kosbrother.com.doctorguide.model;

import android.net.Uri;

import kosbrother.com.doctorguide.BuildConfig;
import kosbrother.com.doctorguide.google_signin.SignInManager;

public class MainModel {

    public static final Uri APP_URI = Uri.parse("android-app://kosbrother.com.doctorguide/http/doctorguide.tw");
    public static final Uri WEB_URL = Uri.parse("http://doctorguide.tw/");

    public String getUserName() {
        return SignInManager.getInstance().getName();
    }

    public String getVersionName() {
        return "APP版本 " + BuildConfig.VERSION_NAME;
    }

    public boolean isSignIn() {
        return SignInManager.getInstance().isSignIn();
    }

}
