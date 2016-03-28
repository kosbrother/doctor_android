package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.HospitalDoctorModel;
import kosbrother.com.doctorguide.view.HospitalDoctorView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HospitalDoctorPresenterTest {

    private HospitalDoctorView view;
    private HospitalDoctorModel model;
    private HospitalDoctorPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(HospitalDoctorView.class);
        model = mock(HospitalDoctorModel.class);
        presenter = new HospitalDoctorPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setActionBar();
    }

    @Test
    public void testOnGetLocationSuccess() throws Exception {
        presenter.onGetLocationSuccess();

        verify(view).setContentView();
        verify(view).setViewPager();
    }

    @Test
    public void testOnListFragmentInteraction_doctor() throws Exception {
        Doctor doctor = new Doctor();

        presenter.onListFragmentInteraction(doctor);

        verify(view).startDoctorActivity(doctor);
    }

    @Test
    public void testOnListFragmentInteraction_hospital() throws Exception {
        Hospital hospital = new Hospital();

        presenter.onListFragmentInteraction(hospital);

        verify(model).setHospital(hospital);
        verify(view).showProgressDialog();
        verify(model).requestGetDivisions(presenter);
    }

    @Test
    public void testOnGetDivisionsSuccess_divisionsMoreThenOne() throws Exception {
        ArrayList<Division> divisions = new ArrayList<>();
        divisions.add(new Division());
        divisions.add(new Division());

        presenter.onGetDivisionsSuccess(divisions);

        verify(model).setDivisions(divisions);
        verify(view).hideProgressDialog();
        verify(view).showDivisionsDialog(model.getDivisionArray(), model.getHospital());
    }

    @Test
    public void testOnGetDivisionsSuccess_oneDivision() throws Exception {
        ArrayList<Division> divisions = new ArrayList<>();
        divisions.add(new Division());

        presenter.onGetDivisionsSuccess(divisions);

        verify(model).setDivisions(divisions);
        verify(view).hideProgressDialog();
        verify(view).startDivisionActivity(divisions, model.getHospital(), 0);
    }

    @Test
    public void testOnDivisionsDialogClick() throws Exception {
        int position = 0;

        presenter.onDivisionsDialogClick(position);

        verify(view).startDivisionActivity(model.getDivisions(), model.getHospital(), position);
    }
}