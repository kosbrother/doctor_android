package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.DoctorFabModel;
import kosbrother.com.doctorguide.view.DoctorFabView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DoctorFabPresenterTest {

    private DoctorFabView view;
    private DoctorFabModel model;
    private DoctorFabPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(DoctorFabView.class);
        model = mock(DoctorFabModel.class);
        presenter = new DoctorFabPresenter(view, model);
    }

    @Test
    public void testOnFabProblemReportClick() throws Exception {
        presenter.onFabProblemReportClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.PROBLEM_REPORT);
        verify(view).startProblemReportActivity(model.getViewModel());
    }

    @Test
    public void testOnFabCommentClick_isSignIn() throws Exception {
        when(model.isSignIn()).thenReturn(true);

        presenter.onFabCommentClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.COMMENT);
        verify(view).startCommentActivity(model.getViewModel(), model.getEmail());
    }

    @Test
    public void testOnFabCommentClick_notSignIn() throws Exception {
        when(model.isSignIn()).thenReturn(false);

        presenter.onFabCommentClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.COMMENT);
        verify(view).showSignInDialog();
    }

    @Test
    public void testOnSignInButtonClick_withNetwork() throws Exception {
        when(view.isNetworkConnected()).thenReturn(true);

        presenter.onSignInButtonClick();

        verify(view).dismissSignInDialog();
        verify(view).signIn();
    }

    @Test
    public void testOnSignInButtonClick_noNetwork() throws Exception {
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