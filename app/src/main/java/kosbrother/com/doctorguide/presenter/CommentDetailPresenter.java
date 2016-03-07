package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.CommentDetailView;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.model.CommentDetailModel;
import kosbrother.com.doctorguide.task.GetCommentTask;

public class CommentDetailPresenter implements GetCommentTask.GetCommentListener {
    private final CommentDetailView view;
    private final CommentDetailModel model;

    public CommentDetailPresenter(CommentDetailView view, CommentDetailModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.initActionBar();

        view.showProgressDialog();
        model.requestGetComment(this);
    }

    @Override
    public void onGetCommentResult(Comment comment) {
        model.initResultData(comment);
        view.hideProgressDialog();

        view.setCommentInfo(model.getCommentInfoViewModel());
        view.setDivisionComment(model.getDivisionCommentViewModel());
        view.setDoctorComment(model.getDoctorCommentViewModel());
    }

}
