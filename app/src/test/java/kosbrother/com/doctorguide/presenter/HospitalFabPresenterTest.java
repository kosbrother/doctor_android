package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.HospitalFabModel;
import kosbrother.com.doctorguide.view.HospitalFabView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HospitalFabPresenterTest {

    private HospitalFabView view;
    private HospitalFabModel model;
    private HospitalFabPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(HospitalFabView.class);
        model = mock(HospitalFabModel.class);
        presenter = new HospitalFabPresenter(view, model);
    }

    @Test
    public void testOnProblemReportClick() throws Exception {
        presenter.onProblemReportClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.PROBLEM_REPORT);
        verify(view).startProblemReportActivity(model.getViewModel());
    }

    @Test
    public void testOnAddDoctorClick() throws Exception {
        presenter.onAddDoctorClick();

        verify(view).closeFab();
        verify(view).sendClickFabEvent(GALabel.ADD_DOCTOR);
        verify(view).startAddDoctorActivity(model.getViewModel());
    }
}