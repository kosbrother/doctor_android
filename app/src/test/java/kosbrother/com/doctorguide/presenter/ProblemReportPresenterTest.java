package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.model.ProblemReportModel;
import kosbrother.com.doctorguide.view.ProblemReportView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProblemReportPresenterTest {

    private ProblemReportView view;
    private ProblemReportModel model;
    private ProblemReportPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(ProblemReportView.class);
        model = mock(ProblemReportModel.class);
        presenter = new ProblemReportPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).setReportType(model.getReportTypeText());
        verify(view).setReportPage(model.getReportPageText());
    }

    @Test
    public void testOnSubmitClick_withContent() throws Exception {
        String content = "content";

        presenter.onSubmitClick(content);

        verify(view).showProgressDialog();
        verify(model).requestPostProblem(content, presenter);
    }

    @Test
    public void testOnSubmitClick_noContent() throws Exception {
        String content = "";

        presenter.onSubmitClick(content);

        verify(view).showNoContentSnackBar();
    }

    @Test
    public void testOnPostProblemSuccess() throws Exception {
        presenter.onPostProblemSuccess();

        verify(view).hideProgressDialog();
        verify(view).showSubmitSuccessDialog();
    }
}