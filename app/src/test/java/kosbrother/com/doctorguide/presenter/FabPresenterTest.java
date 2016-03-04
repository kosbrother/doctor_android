package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.model.FabModel;
import kosbrother.com.doctorguide.view.FabView;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).initFab();
    }

    @Test
    public void testOnFabMenuToggle() throws Exception {
        boolean opened = anyBoolean();

        presenter.onFabMenuToggle(opened);

        verify(view).sendClickFabEvent(GALabel.FAB_MENU);
        verify(view).setFabImageDrawable(model.getFabDrawableId(opened));
    }

    @Test
    public void testOnFabProblemReportClick() throws Exception {
        presenter.onFabProblemReportClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.PROBLEM_REPORT);
        verify(view).startProblemReportActivity(model.getViewModel());
    }

    @Test
    public void testOnFabShareClick() throws Exception {
        presenter.onFabShareClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.SHARE);
        verify(view).startShareActivity();
    }

    @Test
    public void testOnFabCommentClick_isSignin() throws Exception {
        presenter.onFabCommentClick(true);

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.COMMENT);
        verify(view).startCommentActivity(model.getViewModel(), model.getEmail());
    }

    @Test
    public void testOnFabCommentClick_notSignin() throws Exception {
        presenter.onFabCommentClick(false);

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
    public void testOnSignInButtonClick() throws Exception {
        presenter.onSignInButtonClick();

        verify(view).signIn();
        verify(view).dismissSignInDialog();
    }

    @Test
    public void testOnHandleSignInResultSuccess() throws Exception {
        String email = "email";

        presenter.onHandleSignInResultSuccess(email);

        verify(model).setEmail(email);
    }

    @Test
    public void testOnSignInActivityResultSuccess() throws Exception {
        User user = new User();

        presenter.onSignInActivityResultSuccess(user);

        verify(view).showProgressDialog();
        verify(model).requestCreateUser(user, presenter);
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