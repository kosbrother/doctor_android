package kosbrother.com.doctorguide.Util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import kosbrother.com.doctorguide.BaseActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.google_signin.SignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;

public abstract class SignInActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        CreateUserTask.CreateUserListener {

    private GoogleApiClient mGoogleApiClient;
    protected static final int RC_SIGN_IN = 9002;
    private Dialog dialog;
    private ProgressDialog mProgressDialog;
    private CallbackManager fbCallbackManager;
    private FacebookCallback<LoginResult> fbCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            dialog.dismiss();
            GraphRequest request = getGraphRequest(loginResult);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "email");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(FacebookException error) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFbLogin();
        buildGoogleApiClient();
    }

    private void initFbLogin() {
        fbCallbackManager = CallbackManager.Factory.create();
    }

    private void buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showLoginFailToast();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            showProgressDialog();
            User googleUser = getGoogleUser(result);
            SignInManager.getInstance().saveUser(googleUser);
            startCreateUserTask(googleUser);
        }
    }

    private User getGoogleUser(GoogleSignInResult result) {
        User user = new User();
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                user.id = acct.getId();
                user.email = acct.getEmail();
                user.name = acct.getDisplayName();
                user.pic_url = getPhotoUrl(acct);
            }
        }
        return user;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void showRequireNetworkDialog() {
        new AlertDialog.Builder(this)
                .setTitle("訊息通知")
                .setMessage("就醫指南需要網路才能運行，請按確認鍵至手機設定畫面，開啟網路連結，謝謝！")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SignInActivity.this.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .show();
    }

    public void showSignInDialog() {
        setSignInDialog(R.layout.dialog_login);
        dialog.show();
    }

    public void showMyCommentSingInDialog() {
        setSignInDialog(R.layout.dialog_login_enter_mycomment);
        dialog.show();
    }

    private void setSignInDialog(int layoutResID) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutResID);
        SignInButton signInBtn = (SignInButton) dialog.findViewById(R.id.google_sign_in_button);
        signInBtn.setSize(SignInButton.SIZE_WIDE);
        signInBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                signIn();
            }
        });
        LoginButton fbLoginButton = (LoginButton) dialog.findViewById(R.id.facebook_sign_in_button);
        fbLoginButton.setReadPermissions("public_profile", "email");
        fbLoginButton.registerCallback(fbCallbackManager, fbCallback);
    }

    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(this);
    }

    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    private void startCreateUserTask(User user) {
        new CreateUserTask(SignInActivity.this).execute(user);
    }

    @Override
    public void onCreateUserSuccess() {
        hideProgressDialog();
        afterCreateUserSuccess();
    }

    @Override
    public void onCreateUserFail() {
        hideProgressDialog();
        showLoginFailToast();
    }

    private void showLoginFailToast() {
        Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_LONG).show();
    }

    private String getPhotoUrl(GoogleSignInAccount acct) {
        Uri photoUrl = acct.getPhotoUrl();
        return photoUrl == null ? "" : photoUrl.toString();
    }

    @NonNull
    private GraphRequest getGraphRequest(LoginResult loginResult) {
        return GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        showProgressDialog();
                        User user = getFbUser(object, Profile.getCurrentProfile());
                        SignInManager.getInstance().saveUser(user);
                        startCreateUserTask(user);
                    }
                });
    }

    @NonNull
    private User getFbUser(JSONObject object, Profile profile) {
        String userEmail = "";
        User user = new User();
        user.id = profile.getId();
        user.name = profile.getName();
        user.pic_url = profile.getProfilePictureUri(50, 50).toString();
        try {
            userEmail = object.getString("email");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        user.email = userEmail;
        return user;
    }

    protected abstract void afterCreateUserSuccess();

}
