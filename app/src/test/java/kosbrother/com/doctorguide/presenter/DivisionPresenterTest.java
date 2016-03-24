package kosbrother.com.doctorguide.presenter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.DivisionModel;
import kosbrother.com.doctorguide.view.DivisionView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DivisionPresenterTest {

    private DivisionView view;
    private DivisionModel model;
    private DivisionPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(DivisionView.class);
        model = mock(DivisionModel.class);
        presenter = new DivisionPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initActionBar();
        verify(view).setHospitalNameFromHtml(model.getHospitalNameWithUnderline());
        verify(view).setDivisionImage(model.getDivisionImageResId());

        verify(view).showProgressDialog();
        verify(model).requestGetDivision(presenter);
        verify(model).requestGetDivisions(presenter);
    }

    @Test
    public void testOnGetDivisionsSuccess() throws Exception {
        ArrayList<Division> divisions = new ArrayList<>();

        presenter.onGetDivisionsSuccess(divisions);

        verify(model).setDivisions(divisions);
        verify(view).setupSpinner(model.getDivisionNames(), model.getDivisionName());
    }

    @Test
    public void testOnGetDivisionSuccess() throws Exception {
        Division division = new Division();

        presenter.onGetDivisionSuccess(division);

        verify(model).setDivision(division);
        verify(view).setDivisionScoreText(model.getDivisionScoreViewModel());
        verify(view).setupViewPager(model.getHospitalId(), model.getDivisionId());
        verify(view).hideProgressDialog();
    }

    @Test
    public void testOnHospitalTextViewClick() throws Exception {
        presenter.onHospitalTextViewClick();

        String hospitalName = model.getHospitalName();
        verify(view).sendDivisionClickHospitalTextEvent(hospitalName);
        verify(view).startHospitalActivity(model.getDivisionInputViewModel());
    }

    @Test
    public void testOnDivisionSpinnerItemClick() throws Exception {
        int position = 0;

        presenter.onDivisionSpinnerItemClick(position);

        verify(view).sendDivisionClickDivisionSpinnerEvent(model.getClickDivisionName(position));
        verify(view).startDivisionActivity(model.getDivisionInputViewModel(),
                model.getClickDivisionId(position),
                model.getClickDivisionName(position));
        verify(view).finish();
    }

    @Test
    public void testOnListFragmentDoctorClick() throws Exception {
        Doctor doctor = new Doctor();

        presenter.onListFragmentDoctorClick(doctor);

        verify(view).startDoctorActivity(doctor, model.getDivisionInputViewModel());
    }

    @Test
    public void testOnListFragmentHeartClick_DoctorCollected() throws Exception {
        Doctor doctor = new Doctor();
        doctor.isCollected = true;

        presenter.onListFragmentHeartClick(doctor);

        verify(model).setDoctorId(doctor.id);
        verify(view).showCancelCollectDialog(model.getCancelCollectMessage(doctor.name));
    }

    @Test
    public void testOnListFragmentHeartClick_DoctorNotCollected() throws Exception {
        Doctor doctor = new Doctor();
        doctor.isCollected = false;

        presenter.onListFragmentHeartClick(doctor);

        verify(view).executeCollectDoctor(doctor, model.getHospitalName(), model.getHospitalId());
        verify(view).updateAdapter();
        verify(view).showCollectSuccessSnackBar();
    }

    @Test
    public void testOnConfirmCancelCollectClick() throws Exception {
        presenter.onConfirmCancelCollectClick();

        verify(view).updateAdapter();
        verify(view).executeCancelCollectDoctor(model.getHospitalId());
    }

    @Test
    public void testGetDivision() throws Exception {
        Division expected = model.getDivision();

        Division actual = presenter.getDivision();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testOnAddDoctorClick() throws Exception {
        presenter.onAddDoctorClick();

        verify(view).sendDivisionAddDoctorClickEvent(model.getDivisionLabel());
    }

    @Test
    public void testOnAddCommentClick() throws Exception {
        presenter.onAddCommentClick();

        verify(view).sendDivisionClickAddCommentEvent(model.getDivisionLabel());
    }
}