package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.view.ProgressDialogView;

public interface FeedbackView extends ProgressDialogView{
    void setContentView();

    void setActionBar();

    void showNoTitleSnackBar();

    void showNoContentSnackBar();

    void showPostCommentSuccessDialog();
}
