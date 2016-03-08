package kosbrother.com.doctorguide.google_signin;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import kosbrother.com.doctorguide.entity.User;

public class GoogleSignInManager implements GoogleApiClient.OnConnectionFailedListener {
    private static final String PREF_USER = "PREF_USER";
    private static final String USER_EMAIL = "USER_EMAIL";
    private static final String USER_NAME = "USER_NAME";
    private static final String USER_PIC_URL = "USER_PIC_URL";

    private static GoogleSignInManager instance;
    private Context context;

    private GoogleSignInManager() {
    }

    public static GoogleSignInManager getInstance() {
        if (instance == null) {
            instance = new GoogleSignInManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                editUserData(acct.getEmail(), acct.getDisplayName(), getPhotoUrl(acct));
            }
        }
    }

    public User getUser() {
        User user = new User();
        user.email = getEmail();
        user.name = getName();
        user.pic_url = getPicUrl();
        return user;
    }

    public boolean isSignIn() {
        return !getEmail().isEmpty();
    }

    public void clearUserData() {
        editUserData("", "", "");
    }

    @NonNull
    public String getEmail() {
        return getSharedPreferences().getString(USER_EMAIL, "");
    }

    @NonNull
    public String getName() {
        return getSharedPreferences().getString(USER_NAME, "");
    }

    @NonNull
    private String getPicUrl() {
        return getSharedPreferences().getString(USER_PIC_URL, "");
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREF_USER, 0);
    }

    private void editUserData(String email, String name, String photoUrl) {
        SharedPreferences.Editor edit = getSharedPreferences().edit();
        edit.putString(USER_EMAIL, email);
        edit.putString(USER_NAME, name);
        edit.putString(USER_PIC_URL, photoUrl);
        edit.apply();
    }

    private String getPhotoUrl(GoogleSignInAccount acct) {
        Uri photoUrl = acct.getPhotoUrl();
        return photoUrl == null ? "" : photoUrl.toString();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
