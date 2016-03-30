package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.MainModel;
import kosbrother.com.doctorguide.view.MainView;

public class MainPresenter {
    private MainView view;
    private MainModel model;

    public MainPresenter(MainView view, MainModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.buildAppIndexClient();
        view.setToolBarAndDrawer();
        view.setNavigationView();
        view.setAppVersionName(model.getVersionName());
        view.setTabAndViewPager();
    }

    public void onStart() {
        if (!view.isNetworkConnected()) {
            view.showRequireNetworkDialog();
        } else {
            handleSignInInfo();
            view.connectAppIndexClient();
            view.startAppIndexApi(MainModel.WEB_URL, MainModel.APP_URI);
        }
    }

    public void onStop() {
        view.endAppIndexApi(MainModel.WEB_URL, MainModel.APP_URI);
        view.disConnectAppIndexClient();
    }

    public void onSignInButtonClick() {
        view.sendMainClickAccountEvent(GALabel.SIGN_IN);
        if (!view.isNetworkConnected()) {
            view.showRequireNetworkDialog();
        } else {
            view.closeDrawer();
            view.showSignInDialog();
        }
    }

    public void onBackPressedWhenDrawerOpen() {
        view.closeDrawer();
    }

    public void onSearchViewClick() {
        view.sendMainClickSearchIconEvent();
    }

    public void onQueryTextSubmit(String query) {
        view.sendMainSubmitSearchTextEvent(query);
        view.initSearchView();
    }

    public void onAccountMenuItemSelected() {
        view.sendMainClickAccountEvent(GALabel.MENU_ITEM);
        view.toggleDrawer();
    }

    public void onNavigationMyCollectionsClick() {
        view.closeDrawer();
        view.sendMainClickAccountEvent(GALabel.MY_COLLECTION);
        view.startMyCollectionActivity();
    }

    public void onNavigationMyCommentClick() {
        view.closeDrawer();
        view.sendMainClickAccountEvent(GALabel.MY_COMMENT);
        view.startMyCommentActivity();
    }

    public void onNavigationSettingClick() {
        view.closeDrawer();
        view.sendMainClickAccountEvent(GALabel.SETTING);
        view.startSettingActivity();
    }

    public void onNavigationFeedbackClick() {
        view.closeDrawer();
        view.sendMainClickAccountEvent(GALabel.FEEDBACK);
        view.startFeedbackActivity();
    }

    public void onNavigationPlayStoreClick() {
        view.closeDrawer();
        view.sendMainClickAccountEvent(GALabel.PLAY_STORE);
    }

    public void afterCreateUserSuccess() {
        showUserInfo();
    }

    protected void handleSignInInfo() {
        if (model.isSignIn()) {
            showUserInfo();
        } else {
            view.hideUserName();
            view.showSignInButton();
        }
    }

    protected void showUserInfo() {
        view.setUserName(model.getUserName());
        view.showUserName();
        view.hideSignInButton();
    }
}
