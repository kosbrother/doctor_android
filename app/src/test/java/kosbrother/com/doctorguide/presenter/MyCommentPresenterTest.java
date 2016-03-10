package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.model.MyCommentModel;
import kosbrother.com.doctorguide.view.MyCommentView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyCommentPresenterTest {

    private MyCommentView view;
    private MyCommentModel model;
    private MyCommentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(MyCommentView.class);
        model = mock(MyCommentModel.class);
        presenter = new MyCommentPresenter(view, model);
    }

    @Test
    public void testOnCreate_userSignIn() throws Exception {
        when(model.isUserSignIn()).thenReturn(true);

        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).showProgressDialog();
        verify(model).requestGetMyComments(presenter);
    }

    @Test
    public void testOnCreate_userNotSignIn() throws Exception {
        when(model.isUserSignIn()).thenReturn(false);

        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).showSingInDialog();
    }

    @Test
    public void testOnGetMyCommentsSuccess_noComments() throws Exception {
        ArrayList<Comment> comments = new ArrayList<>();

        presenter.onGetMyCommentsSuccess(comments);

        verify(view).showNoCommentLayout();
        verify(view).hideProgressDialog();
    }

    @Test
    public void testOnGetMyCommentsSuccess_withComments() throws Exception {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(new Comment());

        presenter.onGetMyCommentsSuccess(comments);

        verify(view).setRecyclerView(comments);
        verify(view).hideProgressDialog();
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

        verify(model).requestGetMyComments(presenter);
    }

    @Test
    public void testOnCreateUserFail() throws Exception {
        presenter.onCreateUserFail();

        verify(view).hideProgressDialog();
        verify(view).showCreateUserFailToast();
    }
}