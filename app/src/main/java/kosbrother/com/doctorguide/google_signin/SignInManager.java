package kosbrother.com.doctorguide.google_signin;

import android.content.Context;
import android.content.SharedPreferences;

import kosbrother.com.doctorguide.entity.User;

public class SignInManager {
    private static final String PREF_USER = "PREF_USER";
    private static final String USER_ID = "USER_ID";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_PIC_URL = "USER_PIC_URL";

    private static SignInManager instance;
    private Context context;

    private SignInManager() {
    }

    public static SignInManager getInstance() {
        if (instance == null) {
            instance = new SignInManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public boolean isSignIn() {
        return !getUserSharedPreferences().getString(USER_ID, "").isEmpty();
    }

    public String getEmail() {
        return getUserSharedPreferences().getString(USER_EMAIL, "");
    }

    public String getName() {
        return getUserSharedPreferences().getString(USER_NAME, "");
    }

    public void saveUser(User user) {
        editUserData(getUserSharedPreferences(), user);
    }

    public void clearUserData() {
        editUserData(getUserSharedPreferences(), getEmptyUser());
    }

    private void editUserData(SharedPreferences sharedPreferences, User user) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(USER_ID, user.id);
        edit.putString(USER_EMAIL, user.email);
        edit.putString(USER_NAME, user.name);
        edit.putString(USER_PIC_URL, user.pic_url);
        edit.apply();
    }

    private SharedPreferences getUserSharedPreferences() {
        return context.getSharedPreferences(PREF_USER, 0);
    }

    private User getEmptyUser() {
        User user = new User();
        user.id = "";
        user.name = "";
        user.email = "";
        user.pic_url = "";
        return user;
    }
}
