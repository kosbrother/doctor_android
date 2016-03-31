package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.model.MyCommentModel;
import kosbrother.com.doctorguide.task.GetMyCommentsTask;
import kosbrother.com.doctorguide.view.MyCommentView;

public class MyCommentPresenter implements GetMyCommentsTask.GetMyCommentsListener {
    private final MyCommentView view;
    private final MyCommentModel model;

    public MyCommentPresenter(MyCommentView view, MyCommentModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();

        if (model.isUserSignIn()) {
            view.showProgressDialog();
            model.requestGetMyComments(this);
        } else {
            view.showMyCommentSingInDialog();
        }
    }

    @Override
    public void onGetMyCommentsSuccess(ArrayList<Comment> comments) {
        if (comments.size() == 0) {
            view.showNoCommentLayout();
        } else {
            view.setRecyclerView(comments);
        }
        view.hideProgressDialog();
    }

    public void afterCreateUserSuccess() {
        view.showProgressDialog();
        model.requestGetMyComments(this);
    }
}
