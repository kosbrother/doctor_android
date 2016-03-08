package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.HospitalModel;
import kosbrother.com.doctorguide.task.GetHospitalTask;
import kosbrother.com.doctorguide.view.HospitalView;

public class HospitalPresenter implements GetHospitalTask.GetHospitalListener {
    private final HospitalView view;
    private final HospitalModel model;

    public HospitalPresenter(HospitalView view, HospitalModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();
        view.setHospitalImage(model.getHospitalImageRedId());
        view.setHospitalName(model.getHospitalName());
        view.setHeartButtonBackground(model.getHeartButtonBackgroundResId());

        view.showProgressDialog();
        model.requestGetHospital(this);
    }

    @Override
    public void onGetHospitalSuccess(Hospital hospital) {
        model.setHospital(hospital);

        view.setHeartButton();
        view.setHospitalScore(model.getHospitalScoreViewModel());
        view.setViewPager(model.getHospitalId(), model.getHospital());
        view.hideProgressDialog();
    }

    public void onHeartButtonClick() {
        view.sendHospitalClickCollectEvent(model.getHospitalName());
        if (model.isHospitalCollected()) {
            model.removeHospitalFromCollect();
            view.showRemoveFromCollectSuccessSnackBar();
        } else {
            model.addHospitalToCollect();
            view.showAddToCollectSuccessSnackBar();
        }
        view.setHeartButtonBackground(model.getHeartButtonBackgroundResId());
    }

    public void onDivisionInfoClick(Division division) {
        view.showDivisionInfoDialog(division);
    }

    public void onDivisionClick(Division division) {
        view.startDivisionActivity(division);
    }
}
