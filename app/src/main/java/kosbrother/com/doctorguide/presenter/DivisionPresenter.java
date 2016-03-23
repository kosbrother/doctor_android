package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.DivisionModel;
import kosbrother.com.doctorguide.task.GetDivisionTask;
import kosbrother.com.doctorguide.task.GetDivisionsByHospitalTask;
import kosbrother.com.doctorguide.view.DivisionView;

public class DivisionPresenter implements
        GetDivisionTask.GetDivisionListener,
        GetDivisionsByHospitalTask.GetDivisionsListener {

    private final DivisionView view;
    private final DivisionModel model;

    public DivisionPresenter(DivisionView view, DivisionModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.initActionBar();
        view.setDivisionImage(model.getDivisionImageResId());
        view.setHospitalNameFromHtml(model.getHospitalNameWithUnderline());

        view.showProgressDialog();
        model.requestGetDivisions(this);
        model.requestGetDivision(this);
    }

    @Override
    public void onGetDivisionsSuccess(ArrayList<Division> divisions) {
        model.setDivisions(divisions);
        view.setupSpinner(model.getDivisionNames(), model.getDivisionName());
    }

    @Override
    public void onGetDivisionSuccess(Division division) {
        model.setDivision(division);

        view.hideProgressDialog();
        view.setDivisionScoreText(model.getDivisionScoreViewModel());
        view.setupViewPager(model.getHospitalId(), model.getDivisionId());
    }

    public void onHospitalTextViewClick() {
        view.sendDivisionClickHospitalTextEvent(model.getHospitalName());
        view.startHospitalActivity(model.getDivisionInputViewModel());
    }

    public void onDivisionSpinnerItemClick(int position) {
        String clickDivisionName = model.getClickDivisionName(position);
        view.sendDivisionClickDivisionSpinnerEvent(clickDivisionName);
        view.startDivisionActivity(model.getDivisionInputViewModel(),
                model.getClickDivisionId(position),
                model.getClickDivisionName(position));
        view.finish();
    }

    public void onListFragmentDoctorClick(Doctor doctor) {
        view.startDoctorActivity(doctor, model.getDivisionInputViewModel());
    }

    public void onListFragmentHeartClick(Doctor doctor) {
        if (doctor.isCollected) {
            model.setDoctorId(doctor.id);
            view.showCancelCollectDialog(model.getCancelCollectMessage(doctor.name));
        } else {
            view.executeCollectDoctor(doctor, model.getHospitalName(), model.getHospitalId());
            view.updateAdapter();
            view.showCollectSuccessSnackBar();
        }
    }

    public void onConfirmCancelCollectClick() {
        view.updateAdapter();
        view.executeCancelCollectDoctor(model.getDoctorId());
    }

    public Division getDivision() {
        return model.getDivision();
    }

    public void onAddDoctorClick() {
        view.sendDivisionAddDoctorClickEvent(model.getDivisionLabel());
    }

    public void onAddCommentClick() {
        view.sendDivisionClickAddCommentEvent(model.getDivisionLabel());
    }
}
