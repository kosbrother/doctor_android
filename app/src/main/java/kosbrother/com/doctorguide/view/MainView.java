package kosbrother.com.doctorguide.view;

public interface MainView extends ProgressDialogView{
    void setContentView();

    void setToolBarAndDrawer();

    void setNavigationView();

    void setAppVersionName(String versionName);

    void setRecyclerView();

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

}
