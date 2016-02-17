package kosbrother.com.doctorguide;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class DoctorGuideApplication extends Application {
    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
        // To enable debug logging use:
        // adb shell setprop log.tag.GAv4 DEBUG
        // adb logcat -s GAv4
        if (BuildConfig.DEBUG) {
            analytics.setDryRun(true);
        }
        mTracker = analytics.newTracker("UA-73843935-1");
        mTracker.enableAutoActivityTracking(true);
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        return mTracker;
    }

}
