package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.view.DoctorFabView;

public class DoctorFabPresenter {

    private final DoctorFabView view;

    public DoctorFabPresenter(DoctorFabView view) {
        this.view = view;
    }

    public void onFabAddCommentClick() {
        view.sendClickFabEvent(GALabel.COMMENT);
    }

}
