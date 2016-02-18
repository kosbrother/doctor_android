package kosbrother.com.doctorguide;

import android.app.Application;

import kosbrother.com.doctorguide.google_analytics.GAManager;

public class DoctorGuideApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GAManager.init(this);
    }

}
