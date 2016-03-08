package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.DoctorFabModel;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.view.DoctorFabView;

public class DoctorFabPresenter extends BaseFabPresenter implements
        CreateUserTask.CreateUserListener {

    private final DoctorFabView view;
    private final DoctorFabModel model;

    public DoctorFabPresenter(DoctorFabView view, DoctorFabModel model) {
        super(view, model);
        this.view = view;
        this.model = model;
    }

    public void onFabProblemReportClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.PROBLEM_REPORT);
        view.startProblemReportActivity(model.getViewModel());
    }

    public void onFabCommentClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.COMMENT);
        if (model.isSignIn()) {
            view.startCommentActivity(model.getViewModel(), model.getEmail());
        } else {
            view.showSignInDialog();
        }
    }

    public void onSignInButtonClick() {
        view.dismissSignInDialog();
        if (!view.isNetworkConnected()) {
            view.showRequireNetworkDialog();
            return;
        }
        view.signIn();
    }

    public void onSignInActivityResultSuccess() {
        view.showProgressDialog();
        model.requestCreateUser(this);
    }

    @Override
    public void onCreateUserSuccess() {
        view.hideProgressDialog();
        view.startCommentActivity(model.getViewModel(), model.getEmail());
    }

    @Override
    public void onCreateUserFail() {
        view.hideProgressDialog();
        view.showCreateUserFailToast();
    }
}
