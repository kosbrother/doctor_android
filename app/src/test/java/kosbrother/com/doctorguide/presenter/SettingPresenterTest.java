package kosbrother.com.doctorguide.presenter;

import com.google.android.gms.common.api.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import kosbrother.com.doctorguide.model.SettingModel;
import kosbrother.com.doctorguide.google_signin.GoogleSigninInteractor;
import kosbrother.com.doctorguide.view.SettingView;

import static org.mockito.Mockito.verify;

public class SettingPresenterTest {

    private SettingView view;
    private SettingModel model;
    private GoogleSigninInteractor googleSigninInteractor;
    private SettingPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = Mockito.mock(SettingView.class);
        model = Mockito.mock(SettingModel.class);
        googleSigninInteractor = Mockito.mock(GoogleSigninInteractor.class);
        presenter = Mockito.spy(new SettingPresenter(view, model, googleSigninInteractor));
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initActionBar();
    }

    @Test
    public void testOnAboutUsClick() throws Exception {
        presenter.onAboutUsClick();

        verify(view).startAboutUsActivity();
    }

    @Test
    public void testOnLogoutClick() throws Exception {
        presenter.onSignOutClick();

        verify(googleSigninInteractor).signOut(presenter);
    }

    @Test
    public void testOnGoogleSignInResult() throws Exception {
        presenter.onResult(new Status(200));

        verify(view).showSignOutSuccessSnackBar();
        verify(model).clearUserData();
    }
}