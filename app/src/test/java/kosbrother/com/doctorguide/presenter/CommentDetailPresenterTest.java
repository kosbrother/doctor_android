package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.model.CommentDetailModel;
import kosbrother.com.doctorguide.view.CommentDetailView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CommentDetailPresenterTest {

    private CommentDetailView view;
    private CommentDetailModel model;
    private CommentDetailPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(CommentDetailView.class);
        model = mock(CommentDetailModel.class);
        presenter = new CommentDetailPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initActionBar();
        verify(view).showProgressDialog();
        verify(model).requestGetComment(presenter);
    }

    @Test
    public void testOnGetCommentResult() throws Exception {
        Comment comment = new Comment();

        presenter.onGetCommentResult(comment);

        verify(model).initResultData(comment);
        verify(view).hideProgressDialog();
        verify(view).setCommentInfo(model.getCommentInfoViewModel());
        verify(view).setDivisionComment(model.getDivisionCommentViewModel());
        verify(view).setDoctorComment(model.getDoctorCommentViewModel());
    }

}