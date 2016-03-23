package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;

public interface ClickAddCommentView extends ProgressDialogView {
    void showSignInDialog();

    void dismissSignInDialog();

    void showCreateUserFailToast();

    void showRequireNetworkDialog();

    void signIn();

    boolean isNetworkConnected();

    void startAddCommentActivity(AddCommentViewModel addCommentViewModel);
}
