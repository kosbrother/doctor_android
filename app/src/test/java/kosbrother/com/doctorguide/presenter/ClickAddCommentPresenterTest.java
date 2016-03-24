package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.model.ClickAddCommentModel;
import kosbrother.com.doctorguide.view.ClickAddCommentView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClickAddCommentPresenterTest {

    private ClickAddCommentView view;
    private ClickAddCommentModel model;
    private ClickAddCommentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(ClickAddCommentView.class);
        model = mock(ClickAddCommentModel.class);
        presenter = new ClickAddCommentPresenter(view, model);
    }

    @Test
    public void testStartAddComment_isSignIn() throws Exception {
        when(model.isSignIn()).thenReturn(true);

        presenter.startAddComment();

        verify(view).startAddCommentActivity(model.getAddCommentViewModel());
    }

    @Test
    public void testStartAddComment_notSignIn() throws Exception {
        when(model.isSignIn()).thenReturn(false);

        presenter.startAddComment();

        verify(view).showSignInDialog();
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
        verify(view).startAddCommentActivity(model.getAddCommentViewModel());
    }

    @Test
    public void testOnCreateUserFail() throws Exception {
        presenter.onCreateUserFail();

        verify(view).hideProgressDialog();
        verify(view).showCreateUserFailToast();
    }
}