package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.AddDoctorModel;
import kosbrother.com.doctorguide.task.SubmitAddDoctorTask;
import kosbrother.com.doctorguide.view.AddDoctorView;

public class AddDoctorPresenter implements SubmitAddDoctorTask.SubmitAddDoctorListener {
    private final AddDoctorView view;
    private final AddDoctorModel model;

    public AddDoctorPresenter(AddDoctorView view, AddDoctorModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.initActionBar();
        view.setDivisionText(model.getDivisionName());
        view.setHospitalText(model.getHospitalName());
    }

    public void onSubmitClick(String doctorName, String hospitalName) {
        if (doctorName.equals("")) {
            view.sendAddDoctorSubmitEvent(GALabel.NO_DOCTOR_NAME);
            view.showNoDoctorSnackBar();
        } else if (hospitalName.equals("")) {
            view.sendAddDoctorSubmitEvent(GALabel.NO_HOSPITAL_NAME);
            view.showNoHospitalSnackBar();
        } else {
            view.sendAddDoctorSubmitEvent(GALabel.DATA_FILLED);
            view.showProgressDialog();
            model.requestSubmitAddDoctor(view.getSubmitData(), this);
        }
    }

    @Override
    public void onSubmitResultSuccess() {
        view.hideProgressDialog();
        view.showSubmitSuccessDialog();
    }

}
