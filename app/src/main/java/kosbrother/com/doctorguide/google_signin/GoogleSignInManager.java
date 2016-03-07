package kosbrother.com.doctorguide.google_signin;

import android.content.Context;
import android.content.SharedPreferences;
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
    private String email;
    private String name;
    private String picUrl;
    private User user;

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
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_USER, 0);
        email = sharedPreferences.getString(USER_EMAIL, "");
        name = sharedPreferences.getString(USER_NAME, "");
        picUrl = sharedPreferences.getString(USER_PIC_URL, "");
    }

    public void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                user = createUser(acct);
                SharedPreferences.Editor edit = context.getSharedPreferences(PREF_USER, 0).edit();
                edit.putString(USER_EMAIL, user.email);
                edit.putString(USER_NAME, user.name);
                edit.putString(USER_PIC_URL, user.pic_url);
                edit.apply();
            }
        }
    }

    private User createUser(GoogleSignInAccount acct) {
        User user = new User();
        user.email = acct.getEmail();
        user.name = acct.getDisplayName();
        if (acct.getPhotoUrl() != null) {
            user.pic_url = acct.getPhotoUrl().toString();
        }
        return user;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
