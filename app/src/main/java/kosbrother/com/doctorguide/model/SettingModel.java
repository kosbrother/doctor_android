package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.google_signin.SignInManager;

public class SettingModel {
    public void clearUserData() {
        SignInManager.getInstance().clearUserData();
    }
}
