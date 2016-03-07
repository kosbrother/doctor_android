package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;

public class SettingModel {
    public void clearUserData() {
        GoogleSignInManager.getInstance().clearUserData();
    }
}
