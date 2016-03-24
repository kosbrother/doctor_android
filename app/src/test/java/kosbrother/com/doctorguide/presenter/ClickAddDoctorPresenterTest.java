package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.model.ClickAddDoctorModel;
import kosbrother.com.doctorguide.view.ClickAddDoctorView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClickAddDoctorPresenterTest {

    private ClickAddDoctorView view;
    private ClickAddDoctorModel model;
    private ClickAddDoctorPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(ClickAddDoctorView.class);
        model = mock(ClickAddDoctorModel.class);
        presenter = new ClickAddDoctorPresenter(view, model);
    }

    @Test
    public void testStartAddDoctor() throws Exception {
        presenter.startAddDoctor();

        verify(view).startAddDoctorActivity(model.getAddDoctorViewModel());
    }
}