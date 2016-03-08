package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.HospitalFabModel;
import kosbrother.com.doctorguide.view.HospitalFabView;

public class HospitalFabPresenter extends BaseFabPresenter {

    private final HospitalFabView view;
    private final HospitalFabModel model;

    public HospitalFabPresenter(HospitalFabView view, HospitalFabModel model) {
        super(view, model);
        this.view = view;
        this.model = model;
    }

    public void onProblemReportClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.PROBLEM_REPORT);
        view.startProblemReportActivity(model.getViewModel());
    }

    public void onAddDoctorClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.ADD_DOCTOR);
        view.startAddDoctorActivity(model.getViewModel());
    }
}
