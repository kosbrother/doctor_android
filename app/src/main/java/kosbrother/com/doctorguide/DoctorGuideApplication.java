package kosbrother.com.doctorguide;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import io.fabric.sdk.android.Fabric;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_signin.SignInManager;

public class DoctorGuideApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GAManager.init(this);
        SignInManager.getInstance().init(this);
        FacebookSdk.sdkInitialize(this);
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

}
