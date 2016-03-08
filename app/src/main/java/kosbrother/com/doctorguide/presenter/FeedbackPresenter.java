package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.FeedbackModel;
import kosbrother.com.doctorguide.view.FeedbackView;
import kosbrother.com.doctorguide.task.PostCommentTask;

public class FeedbackPresenter implements PostCommentTask.PostCommentListener {
    private final FeedbackView view;
    private final FeedbackModel model;

    public FeedbackPresenter(FeedbackView view, FeedbackModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();
    }

    public void onSubmitClick(String title, String content) {
        if (title.isEmpty()) {
            view.showNoTitleSnackBar();
        } else if (content.isEmpty()) {
            view.showNoContentSnackBar();
        } else {
            view.showProgressDialog();
            model.requestPostComment(title, content, this);
        }
    }

    @Override
    public void onPostCommentSuccess() {
        view.hideProgressDialog();
        view.showPostCommentSuccessDialog();
    }
}
