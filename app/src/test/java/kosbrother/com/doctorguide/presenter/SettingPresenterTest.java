package kosbrother.com.doctorguide.presenter;

import com.google.android.gms.common.api.Status;

import junit.framework.TestCase;

import org.mockito.Mockito;

import kosbrother.com.doctorguide.google_signin.GoogleSigninInteractor;
import kosbrother.com.doctorguide.view.SettingView;

import static org.mockito.Mockito.verify;

public class SettingPresenterTest extends TestCase {

    private SettingView view;
    private GoogleSigninInteractor googleSigninInteractor;
    private SettingPresenter presenter;

    public void setUp() throws Exception {
        super.setUp();
        view = Mockito.mock(SettingView.class);
        googleSigninInteractor = Mockito.mock(GoogleSigninInteractor.class);
        presenter = Mockito.spy(new SettingPresenter(view, googleSigninInteractor));
    }

    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initActionBar();
    }

    public void testOnHomeItemSelected() throws Exception {
        presenter.onHomeItemSelected();

        verify(view).finish();
    }

    public void testOnAboutUsClick() throws Exception {
        presenter.onAboutUsClick();

        verify(view).startAboutUsActivity();
    }

    public void testOnLogoutClick() throws Exception {
        presenter.onSignOutClick();

        verify(googleSigninInteractor).signOut(presenter);
    }

    public void testOnGoogleSignInResult() throws Exception {
        presenter.onResult(new Status(200));

        verify(view).showSignOutSuccessSnackBar();
    }
}