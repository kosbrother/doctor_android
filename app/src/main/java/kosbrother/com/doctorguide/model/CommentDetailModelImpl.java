package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.viewmodel.DivisionCommentViewModelImpl;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.task.GetCommentTask;
import kosbrother.com.doctorguide.viewmodel.CommentInfoViewModel;
import kosbrother.com.doctorguide.viewmodel.CommentInfoViewModelImpl;
import kosbrother.com.doctorguide.viewmodel.DivisionCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorCommentViewModelImpl;

public class CommentDetailModelImpl implements CommentDetailModel {
    private int commentId;
    private CommentInfoViewModel commentViewModel;
    private DoctorCommentViewModel doctorCommentViewModel;
    private DivisionCommentViewModel divisionCommentViewModel;

    public CommentDetailModelImpl(int commentId) {
        this.commentId = commentId;
    }

    @Override
    public void requestGetComment(GetCommentTask.GetCommentListener listener) {
        new GetCommentTask(listener).execute(commentId);
    }

    @Override
    public void initResultData(Comment comment) {
        commentViewModel = new CommentInfoViewModelImpl(comment);
        divisionCommentViewModel = new DivisionCommentViewModelImpl(comment);
        doctorCommentViewModel = new DoctorCommentViewModelImpl(comment);
    }

    @Override
    public CommentInfoViewModel getCommentInfoViewModel() {
        return commentViewModel;
    }

    @Override
    public DivisionCommentViewModel getDivisionCommentViewModel() {
        return divisionCommentViewModel;
    }

    @Override
    public DoctorCommentViewModel getDoctorCommentViewModel() {
        return doctorCommentViewModel;
    }
}
