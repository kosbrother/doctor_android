package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.FabModel;
import kosbrother.com.doctorguide.view.FabView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FabPresenterTest {

    private FabView view;
    private FabModel model;
    private FabPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(FabView.class);
        model = mock(FabModel.class);
        presenter = new FabPresenter(view, model);
    }

    @Test
    public void testOnFabCommentClick_isSignin() throws Exception {
        when(model.isSignIn()).thenReturn(true);

        presenter.onFabCommentClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.COMMENT);
        verify(view).startCommentActivity(model.getViewModel(), model.getEmail());
    }

    @Test
    public void testOnFabCommentClick_notSignin() throws Exception {
        when(model.isSignIn()).thenReturn(false);

        presenter.onFabCommentClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.COMMENT);
        verify(view).showSignInDialog();
    }

    @Test
    public void testOnFabAddDoctorClick() throws Exception {
        presenter.onFabAddDoctorClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.ADD_DOCTOR);
        verify(view).startAddDoctorActivity(model.getViewModel());
    }

    @Test
    public void testOnSignInButtonClick_networkConnected() throws Exception {
        when(view.isNetworkConnected()).thenReturn(true);

        presenter.onSignInButtonClick();

        verify(view).dismissSignInDialog();
        verify(view).signIn();
    }

    @Test
    public void testOnSignInButtonClick_networkNotConnected() throws Exception {
        when(view.isNetworkConnected()).thenReturn(false);

        presenter.onSignInButtonClick();

        verify(view).dismissSignInDialog();
        verify(view).showRequireNetworkDialog();
    }

    @Test
    public void testOnSignInActivityResultSuccess() throws Exception {
        presenter.onSignInActivityResultSuccess();

        verify(view).showProgressDialog();
        verify(model).requestCreateUser(presenter);
    }

    @Test
    public void testOnCreateUserSuccess() throws Exception {
        presenter.onCreateUserSuccess();

        verify(view).hideProgressDialog();
        verify(view).startCommentActivity(model.getViewModel(), model.getEmail());
    }

    @Test
    public void testOnCreateUserFail() throws Exception {
        presenter.onCreateUserFail();

        verify(view).hideProgressDialog();
        verify(view).showCreateUserFailToast();
    }
}