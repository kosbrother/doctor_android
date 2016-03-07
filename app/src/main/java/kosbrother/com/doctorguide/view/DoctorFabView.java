package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DoctorFabViewModel;

public interface DoctorFabView extends BaseFabView, ProgressDialogView {
    void showSignInDialog();

    void showRequireNetworkDialog();

    void showCreateUserFailToast();

    void dismissSignInDialog();

    boolean isNetworkConnected();

    void signIn();

    void startProblemReportActivity(DoctorFabViewModel viewModel);

    void startCommentActivity(DoctorFabViewModel viewModel, String email);
}
