package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;

public interface DivisionFabView extends BaseFabView, ProgressDialogView {
    void showSignInDialog();

    void dismissSignInDialog();

    void showCreateUserFailToast();

    void showRequireNetworkDialog();

    void signIn();

    void startCommentActivity(DivisionActivityViewModel viewModel, String email);

    void startAddDoctorActivity(DivisionActivityViewModel viewModel);

    void startProblemReportActivity(DivisionActivityViewModel viewModel);

    boolean isNetworkConnected();
}
