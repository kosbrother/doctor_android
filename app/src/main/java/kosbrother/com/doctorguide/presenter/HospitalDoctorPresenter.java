package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.HospitalDoctorModel;
import kosbrother.com.doctorguide.task.GetDivisionsByHospitalAndCategoryTask.GetDivisionsListener;
import kosbrother.com.doctorguide.view.HospitalDoctorView;

public class HospitalDoctorPresenter implements GetDivisionsListener {
    private final HospitalDoctorView view;
    private final HospitalDoctorModel model;

    public HospitalDoctorPresenter(HospitalDoctorView view, HospitalDoctorModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();

        if (model.getSdkInt() >= 23) {
            view.checkLocationPermission();
        } else {
            view.setGoogleClient();
        }
    }

    public void onPermissionGranted() {
        view.setGoogleClient();
    }

    public void onPermissionNotGranted() {
        view.checkShouldShowRequestPermissionRationale();
    }

    public void onShouldShowRequestPermissionRationale() {
        view.requestLocationPermission();
    }

    public void onShouldNotShowRequestPermissionRationale() {
        view.showRequestPermissionSnackBar();
        view.requestLocationPermission();
    }

    public void onRequestPermissionResultSuccess() {
        view.setGoogleClient();
    }

    public void onRequestPermissionResultDenied() {
        view.showRequestPermissionDeniedSnackBar();
    }

    public void onGetLastLocationNull() {
        view.requestLocationUpdates();
    }

    public void onGetLastLocationSuccess() {
        view.setViewPager();
    }

    public void onStop() {
        view.disconnectGoogleClient();
    }

    public void onLocationChanged() {
        view.setViewPager();
        view.removeLocationUpdatesListener();
    }

    public void onListFragmentInteraction(Hospital hospital) {
        model.setHospital(hospital);
        view.showProgressDialog();
        model.requestGetDivisions(this);
    }

    @Override
    public void onGetDivisionsSuccess(ArrayList<Division> divisions) {
        model.setDivisions(divisions);
        view.hideProgressDialog();
        if (divisions.size() > 1) {
            view.showDivisionsDialog(model.getDivisionArray(), model.getHospital());
        } else {
            view.startDivisionActivity(divisions, model.getHospital(), 0);
        }
    }

    public void onListFragmentInteraction(Doctor doctor) {
        view.startDoctorActivity(doctor);
    }

    public void onDivisionsDialogClick(int position) {
        view.startDivisionActivity(model.getDivisions(), model.getHospital(), position);
    }
}
