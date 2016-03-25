package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.MainModel;
import kosbrother.com.doctorguide.view.MainView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    private MainView view;
    private MainModel model;
    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(MainView.class);
        model = mock(MainModel.class);
        presenter = new MainPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).buildAppIndexClient();
        verify(view).setToolBarAndDrawer();
        verify(view).setAppVersionName(model.getVersionName());
        verify(view).setNavigationView();
        verify(view).setTabAndViewPager();
    }

    @Test
    public void testOnStart_networkConnected() throws Exception {
        when(view.isNetworkConnected()).thenReturn(true);

        presenter.onStart();

        verify(view).silentSignIn();
        verify(view).connectAppIndexClient();
        verify(view).startAppIndexApi(MainModel.WEB_URL, MainModel.APP_URI);
    }

    @Test
    public void testOnStart_networkNotConnected() throws Exception {
        when(view.isNetworkConnected()).thenReturn(false);

        presenter.onStart();

        verify(view).showRequireNetworkDialog();
    }

    @Test
    public void testOnStop() throws Exception {
        presenter.onStop();

        verify(view).endAppIndexApi(MainModel.WEB_URL, MainModel.APP_URI);
        verify(view).disConnectAppIndexClient();
    }

    @Test
    public void testOnSignInButtonClick_networkConnected() throws Exception {
        when(view.isNetworkConnected()).thenReturn(true);

        presenter.onSignInButtonClick();

        verify(view).signIn();
    }

    @Test
    public void testOnSignInButtonClick_networkNotConnected() throws Exception {
        when(view.isNetworkConnected()).thenReturn(false);

        presenter.onSignInButtonClick();

        verify(view).showRequireNetworkDialog();
    }

    @Test
    public void testOnSignInSuccess() throws Exception {
        presenter.onSignInSuccess();

        verify(view).showProgressDialog();
        verify(model).requestCreateUser(presenter);
    }

    @Test
    public void testOnCreateUserSuccess() throws Exception {
        presenter.onCreateUserSuccess();

        verify(view).hideProgressDialog();
        verify(view).setUserName(model.getUserName());
        verify(view).showUserName();
        verify(view).hideSignInButton();
    }

    @Test
    public void testOnCreateUserFail() throws Exception {
        presenter.onCreateUserFail();

        verify(view).hideProgressDialog();
        verify(view).showCreateUserFailToast();
    }

    @Test
    public void testOnSignInFail() throws Exception {
        presenter.onSignInFail();

        verify(view).hideUserName();
        verify(view).showSignInButton();
    }

    @Test
    public void testOnBackPressedWhenDrawerOpen() throws Exception {
        presenter.onBackPressedWhenDrawerOpen();

        verify(view).closeDrawer();
    }

    @Test
    public void testOnSearchViewClick() throws Exception {
        presenter.onSearchViewClick();

        verify(view).sendMainClickSearchIconEvent();
    }

    @Test
    public void testOnQueryTextSubmit() throws Exception {
        String query = "";

        presenter.onQueryTextSubmit(query);

        verify(view).sendMainSubmitSearchTextEvent(query);
        verify(view).initSearchView();
    }

    @Test
    public void testOnAccountMenuItemSelected() throws Exception {
        presenter.onAccountMenuItemSelected();

        verify(view).sendMainClickAccountEvent(GALabel.MENU_ITEM);
        verify(view).toggleDrawer();
    }

    @Test
    public void testOnNavigationMyCollectionsClick() throws Exception {
        presenter.onNavigationMyCollectionsClick();

        verify(view).closeDrawer();
        verify(view).sendMainClickAccountEvent(GALabel.MY_COLLECTION);
        verify(view).startMyCollectionActivity();
    }

    @Test
    public void testOnNavigationMyCommentClick() throws Exception {
        presenter.onNavigationMyCommentClick();

        verify(view).closeDrawer();
        verify(view).sendMainClickAccountEvent(GALabel.MY_COMMENT);
        verify(view).startMyCommentActivity(model.getUserEmail());
    }

    @Test
    public void testOnNavigationSettingClick() throws Exception {
        presenter.onNavigationSettingClick();

        verify(view).closeDrawer();
        verify(view).sendMainClickAccountEvent(GALabel.SETTING);
        verify(view).startSettingActivity();
    }

    @Test
    public void testOnNavigationFeedbackClick() throws Exception {
        presenter.onNavigationFeedbackClick();

        verify(view).closeDrawer();
        verify(view).sendMainClickAccountEvent(GALabel.FEEDBACK);
        verify(view).startFeedbackActivity();
    }

    @Test
    public void testOnNavigationPlayStoreClick() throws Exception {
        presenter.onNavigationPlayStoreClick();

        verify(view).closeDrawer();
        verify(view).sendMainClickAccountEvent(GALabel.PLAY_STORE);
    }

    @Test
    public void testOnConnectionFailed() throws Exception {
        presenter.onConnectionFailed();

        verify(view).showConnectionFailedToast();
    }
}
