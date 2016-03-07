package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public interface FabView extends BaseFabView, ProgressDialogView {
    void showSignInDialog();

    void dismissSignInDialog();

    void showCreateUserFailToast();

    void showRequireNetworkDialog();

    void signIn();

    void startCommentActivity(DivisionAndHospitalViewModel viewModel, String email);

    void startAddDoctorActivity(DivisionAndHospitalViewModel viewModel);

    boolean isNetworkConnected();
}
