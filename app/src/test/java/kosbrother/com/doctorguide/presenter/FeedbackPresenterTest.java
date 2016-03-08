package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.model.FeedbackModel;
import kosbrother.com.doctorguide.presenter.FeedbackPresenter;
import kosbrother.com.doctorguide.view.FeedbackView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class FeedbackPresenterTest {

    private FeedbackView view;
    private FeedbackModel model;
    private FeedbackPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(FeedbackView.class);
        model = mock(FeedbackModel.class);
        presenter = new FeedbackPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
    }

    @Test
    public void testOnSubmitClick_noTitle() throws Exception {
        presenter.onSubmitClick("", "content");

        verify(view).showNoTitleSnackBar();
    }

    @Test
    public void testOnSubmitClick_noContent() throws Exception {
        presenter.onSubmitClick("title", "");

        verify(view).showNoContentSnackBar();
    }

    @Test
    public void testOnSubmitClick_dataFilled() throws Exception {
        String title = "title";
        String content = "content";

        presenter.onSubmitClick(title, content);

        verify(view).showProgressDialog();
        verify(model).requestPostComment(title, content, presenter);
    }

    @Test
    public void testOnPostCommentSuccess() throws Exception {
        presenter.onPostCommentSuccess();

        verify(view).hideProgressDialog();
        verify(view).showPostCommentSuccessDialog();
    }
}