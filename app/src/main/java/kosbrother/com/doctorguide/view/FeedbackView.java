package kosbrother.com.doctorguide.view;

public interface FeedbackView extends ProgressDialogView{
    void setContentView();

    void setActionBar();

    void showNoTitleSnackBar();

    void showNoContentSnackBar();

    void showPostFeedbackSuccessDialog();
}
