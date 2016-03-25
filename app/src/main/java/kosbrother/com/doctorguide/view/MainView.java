package kosbrother.com.doctorguide.view;

import android.net.Uri;

public interface MainView extends ProgressDialogView{
    void setContentView();

    void setToolBarAndDrawer();

    void setNavigationView();

    void setAppVersionName(String versionName);

    void setTabAndViewPager();

    void setUserName(String userName);

    void initSearchView();

    void showUserName();

    void hideUserName();

    void hideSignInButton();

    void showSignInButton();

    void closeDrawer();

    void toggleDrawer();

    boolean isNetworkConnected();

    void showRequireNetworkDialog();

    void showCreateUserFailToast();

    void showConnectionFailedToast();

    void silentSignIn();

    void signIn();

    void sendMainClickAccountEvent(String label);

    void sendMainClickSearchIconEvent();

    void sendMainSubmitSearchTextEvent(String query);

    void startMyCollectionActivity();

    void startMyCommentActivity(String userName);

    void startSettingActivity();

    void startFeedbackActivity();

    void buildAppIndexClient();

    void connectAppIndexClient();

    void disConnectAppIndexClient();

    void startAppIndexApi(Uri webUrl, Uri appUri);

    void endAppIndexApi(Uri webUrl, Uri appUri);
}
