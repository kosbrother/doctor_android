package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.task.GetCommentTask;
import kosbrother.com.doctorguide.viewmodel.CommentInfoViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorCommentViewModel;

public interface CommentDetailModel {
    void requestGetComment(GetCommentTask.GetCommentListener listener);

    void initResultData(Comment comment);

    CommentInfoViewModel getCommentInfoViewModel();

    DivisionCommentViewModel getDivisionCommentViewModel();

    DoctorCommentViewModel getDoctorCommentViewModel();
}
