package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.DivisionFabModel;
import kosbrother.com.doctorguide.view.DivisionFabView;

public class DivisionFabPresenter {

    private final DivisionFabView view;
    private final DivisionFabModel model;

    public DivisionFabPresenter(DivisionFabView view, DivisionFabModel model) {
        this.view = view;
        this.model = model;
    }

    public void onFabCommentClick() {
        view.sendClickFabEvent(GALabel.COMMENT);
    }

    public void onFabAddDoctorClick() {
        view.sendClickFabEvent(GALabel.ADD_DOCTOR);
    }

    public void onPageChanged(int position) {
        if (model.showAddDoctor(position)) {
            view.hideAddCommentFab();
            view.showAddDoctorFab();
        } else if (model.showAddComment(position)) {
            view.hideAddDoctorFab();
            view.showAddCommentFab();
        }
        model.setLastPagePosition(position);
    }
}
