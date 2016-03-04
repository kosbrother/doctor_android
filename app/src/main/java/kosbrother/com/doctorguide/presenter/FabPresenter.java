package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.FabModel;
import kosbrother.com.doctorguide.view.FabView;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.task.CreateUserTask;

public class FabPresenter implements CreateUserTask.CreateUserListener {

    private final FabView view;
    private final FabModel model;

    public FabPresenter(FabView view, FabModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.initFab();
    }

    public void onFabMenuToggle(boolean opened) {
        view.sendClickFabEvent(GALabel.FAB_MENU);
        view.setFabImageDrawable(model.getFabDrawableId(opened));
    }

    public void onFabProblemReportClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.PROBLEM_REPORT);
        view.startProblemReportActivity(model.getViewModel());
    }

    public void onFabShareClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.SHARE);
        view.startShareActivity();
    }

    public void onFabCommentClick(boolean isSignIn) {
        view.closeFab();
        view.sendClickFabEvent(GALabel.COMMENT);
        if (isSignIn) {
            view.startCommentActivity(model.getViewModel(), model.getEmail());
        } else {
            view.showSignInDialog();
        }
    }

    public void onFabAddDoctorClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.ADD_DOCTOR);
        view.startAddDoctorActivity(model.getViewModel());
    }

    public void onSignInButtonClick() {
        view.signIn();
        view.dismissSignInDialog();
    }

    public void onHandleSignInResultSuccess(String email) {
        model.setEmail(email);
    }

    public void onSignInActivityResultSuccess(User user) {
        view.showProgressDialog();
        model.requestCreateUser(user, this);
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
