package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.CommentInfoViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorCommentViewModel;

public interface CommentDetailView {
    void setContentView();

    void initActionBar();

    void showProgressDialog();

    void hideProgressDialog();

    void setCommentInfo(CommentInfoViewModel commentInfoViewModel);

    void setDoctorComment(DoctorCommentViewModel doctorCommentViewModel);

    void setDivisionComment(DivisionCommentViewModel divisionCommentViewModel);
}
