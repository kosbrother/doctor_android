package kosbrother.com.doctorguide.presenter;

import junit.framework.TestCase;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.AddDoctorModel;
import kosbrother.com.doctorguide.view.AddDoctorView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class AddDoctorPresenterTest extends TestCase {

    private AddDoctorView view;
    private AddDoctorModel model;
    private AddDoctorPresenter presenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        view = mock(AddDoctorView.class);
        model = mock(AddDoctorModel.class);
        presenter = spy(new AddDoctorPresenter(view, model));
    }

    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initActionBar();
        verify(view).setDivisionText(model.getDivisionName());
        verify(view).setHospitalText(model.getHospitalName());
    }

    public void testOnSubmitClick_noDoctorName() throws Exception {
        String doctorName = "";
        String hospitalName = "";
        presenter.onSubmitClick(doctorName, hospitalName);

        verify(view).sendAddDoctorSubmitEvent(GALabel.NO_DOCTOR_NAME);
        verify(view).showNoDoctorSnackBar();
    }

    public void testOnSubmitClick_noHospitalName() throws Exception {
        String doctorName = "doctor";
        String hospitalName = "";
        presenter.onSubmitClick(doctorName, hospitalName);

        verify(view).sendAddDoctorSubmitEvent(GALabel.NO_HOSPITAL_NAME);
        verify(view).showNoHospitalSnackBar();
    }

    public void testOnSubmitClick_submitData() throws Exception {
        String doctorName = "doctor";
        String hospitalName = "hospital";
        presenter.onSubmitClick(doctorName, hospitalName);

        verify(view).showProgressDialog();
        verify(model).requestSubmitAddDoctor(view.getSubmitData(), presenter);
    }

    public void testOnSubmitResultSuccess() throws Exception {
        presenter.onSubmitResultSuccess();

        verify(view).hideProgressDialog();
        verify(view).showSubmitSuccessDialog();
    }

    public void testOnHomeItemSelected() throws Exception {
        presenter.onHomeItemSelected();

        verify(view).finish();
    }
}