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
import static org.mockito.Mockito.when;

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
    public void testOnCreate_sdkInkAbove23() throws Exception {
        when(model.getSdkInt()).thenReturn(23);

        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).checkLocationPermission();
    }

    @Test
    public void testOnCreate_sdkInkBelow23() throws Exception {
        when(model.getSdkInt()).thenReturn(22);

        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).setGoogleClient();
    }

    @Test
    public void testOnPermissionGranted() throws Exception {
        presenter.onPermissionGranted();

        verify(view).setGoogleClient();
    }

    @Test
    public void testOnPermissionNotGranted() throws Exception {
        presenter.onPermissionNotGranted();

        verify(view).checkShouldShowRequestPermissionRationale();
    }

    @Test
    public void testOnShouldShowRequestPermissionRationale() throws Exception {
        presenter.onShouldShowRequestPermissionRationale();

        verify(view).requestLocationPermission();
    }

    @Test
    public void testOnShouldNotShowRequestPermissionRationale() throws Exception {
        presenter.onShouldNotShowRequestPermissionRationale();

        verify(view).showRequestPermissionSnackBar();
        verify(view).requestLocationPermission();
    }

    @Test
    public void testOnRequestPermissionResultSuccess() throws Exception {
        presenter.onRequestPermissionResultSuccess();

        verify(view).setGoogleClient();
    }

    @Test
    public void testOnRequestPermissionResultDenied() throws Exception {
        presenter.onRequestPermissionResultDenied();

        verify(view).showRequestPermissionDeniedSnackBar();
    }

    @Test
    public void testOnGetLastLocationNull() throws Exception {
        presenter.onGetLastLocationNull();

        verify(view).requestLocationUpdates();
    }

    @Test
    public void testOnGetLastLocationSuccess() throws Exception {
        presenter.onGetLastLocationSuccess();

        verify(view).setViewPager();
    }

    @Test
    public void testOnStop() throws Exception {
        presenter.onStop();

        verify(view).disconnectGoogleClient();
    }

    @Test
    public void testOnLocationChanged() throws Exception {
        presenter.onLocationChanged();

        verify(view).setViewPager();
        verify(view).removeLocationUpdatesListener();
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