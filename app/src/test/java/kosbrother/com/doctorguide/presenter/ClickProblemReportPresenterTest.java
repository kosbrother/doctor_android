package kosbrother.com.doctorguide.presenter;

import org.junit.Test;

import kosbrother.com.doctorguide.model.ClickProblemReportModel;
import kosbrother.com.doctorguide.view.ClickProblemReportView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClickProblemReportPresenterTest {

    @Test
    public void testOnProblemReportClick() throws Exception {
        ClickProblemReportView view = mock(ClickProblemReportView.class);
        ClickProblemReportModel model = mock(ClickProblemReportModel.class);
        ClickProblemReportPresenter presenter = new ClickProblemReportPresenter(view, model);

        presenter.onProblemReportClick();

        verify(view).startProblemReportActivity(model.getViewModel());
    }
}