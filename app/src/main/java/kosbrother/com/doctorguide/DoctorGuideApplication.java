package kosbrother.com.doctorguide;

import com.crashlytics.android.Crashlytics;

import android.app.Application;

import io.fabric.sdk.android.Fabric;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;

public class DoctorGuideApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GAManager.init(this);
        GoogleSignInManager.getInstance().init(this);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

}
