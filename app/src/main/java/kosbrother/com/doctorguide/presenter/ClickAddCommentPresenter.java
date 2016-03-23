package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.ClickAddCommentModel;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.view.ClickAddCommentView;

public class ClickAddCommentPresenter implements CreateUserTask.CreateUserListener {
    private final ClickAddCommentView view;
    private final ClickAddCommentModel model;

    public ClickAddCommentPresenter(ClickAddCommentView view, ClickAddCommentModel model) {
        this.view = view;
        this.model = model;
    }

    public void startAddComment() {
        if (model.isSignIn()) {
            view.startAddCommentActivity(model.getAddCommentViewModel());
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
        view.startAddCommentActivity(model.getAddCommentViewModel());
    }

    @Override
    public void onCreateUserFail() {
        view.hideProgressDialog();
        view.showCreateUserFailToast();
    }

}
