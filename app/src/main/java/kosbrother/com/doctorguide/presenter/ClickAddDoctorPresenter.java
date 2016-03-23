package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.ClickAddDoctorModel;
import kosbrother.com.doctorguide.view.ClickAddDoctorView;

public class ClickAddDoctorPresenter {
    private final ClickAddDoctorView view;
    private final ClickAddDoctorModel model;

    public ClickAddDoctorPresenter(ClickAddDoctorView view, ClickAddDoctorModel model) {
        this.view = view;
        this.model = model;
    }

    public void startAddDoctor() {
        view.startAddDoctorActivity(model.getAddDoctorViewModel());
    }
}
