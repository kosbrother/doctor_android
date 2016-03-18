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
    }

    public void onGetLocationSuccess() {
        view.setViewPager();
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

    public void onDivisionsDialogClick(int position) {
        view.startDivisionActivity(model.getDivisions(), model.getHospital(), position);
    }

    public void onListFragmentInteraction(Doctor doctor) {
        view.startDoctorActivity(doctor);
    }
}
