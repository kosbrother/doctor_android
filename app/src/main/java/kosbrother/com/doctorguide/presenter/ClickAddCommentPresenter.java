package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.ClickAddCommentModel;
import kosbrother.com.doctorguide.view.ClickAddCommentView;

public class ClickAddCommentPresenter {
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

    public void afterCreateUserSuccess() {
        view.startAddCommentActivity(model.getAddCommentViewModel());
    }
}
