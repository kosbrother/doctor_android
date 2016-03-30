package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;

public interface ClickAddCommentView extends ProgressDialogView {
    void showSignInDialog();

    void startAddCommentActivity(AddCommentViewModel addCommentViewModel);
}
