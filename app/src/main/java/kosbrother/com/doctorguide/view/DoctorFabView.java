package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DoctorActivityViewModel;

public interface DoctorFabView extends BaseFabView, ProgressDialogView {
    void showSignInDialog();

    void showRequireNetworkDialog();

    void showCreateUserFailToast();

    void dismissSignInDialog();

    boolean isNetworkConnected();

    void signIn();

    void startProblemReportActivity(DoctorActivityViewModel viewModel);

    void startCommentActivity(DoctorActivityViewModel viewModel, String email);
}
