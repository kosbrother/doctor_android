package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DivisionFabViewModel;

public interface DivisionFabView extends BaseFabView, ProgressDialogView {
    void showSignInDialog();

    void dismissSignInDialog();

    void showCreateUserFailToast();

    void showRequireNetworkDialog();

    void signIn();

    void startCommentActivity(DivisionFabViewModel viewModel, String email);

    void startAddDoctorActivity(DivisionFabViewModel viewModel);

    void startProblemReportActivity(DivisionFabViewModel viewModel);

    boolean isNetworkConnected();
}
