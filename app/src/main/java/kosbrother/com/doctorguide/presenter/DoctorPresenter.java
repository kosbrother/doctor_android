package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.DoctorModel;
import kosbrother.com.doctorguide.task.GetDoctorTask;
import kosbrother.com.doctorguide.view.DoctorView;

public class DoctorPresenter implements GetDoctorTask.GetDoctorListener {
    private final DoctorView view;
    private final DoctorModel model;

    public DoctorPresenter(DoctorView view, DoctorModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.initActionBar();
        view.initHeartButton();
        view.setHeartButtonBackground(model.getHeartButtonResId());

        view.showProgressDialog();
        model.requestGetDoctor(this);
    }

    public void onHeartButtonClick() {
        view.sendDoctorClickCollectEvent(model.getDoctorName());
        if (model.isDoctorCollected()) {
            model.removeDoctorFromCollect();
            view.showCancelCollectSnackBar();
        } else {
            model.addDoctorToCollect();
            view.showCollectSuccessSnackBar();
        }
        view.setHeartButtonBackground(model.getHeartButtonResId());
    }

    @Override
    public void onGetDoctorSuccess(Doctor doctor) {
        model.setDoctor(doctor);
        view.setDoctorName(model.getDoctorName());
        view.setDoctorScore(model.getDoctorScoreViewModel());
        view.setViewPager(model.getDoctorId());
        view.hideProgressDialog();
    }

    public Doctor getDoctor() {
        return model.getDoctor();
    }

    public void onAddCommentClick() {
        view.sendDoctorClickAddCommentEvent(model.getDoctorLabel());
    }
}
