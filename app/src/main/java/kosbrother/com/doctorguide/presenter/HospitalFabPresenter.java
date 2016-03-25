package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.view.HospitalFabView;

public class HospitalFabPresenter {
    private final HospitalFabView view;

    public HospitalFabPresenter(HospitalFabView view) {
        this.view = view;
    }

    public void onFabAddCommentClick() {
        view.sendClickAddCommentFabEvent(GALabel.COMMENT);
    }
}
