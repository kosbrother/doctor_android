package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.view.ProgressDialogView;
import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public interface FabView extends ProgressDialogView {
    void initFab();

    void closeFab();

    void setFabImageDrawable(int fabDrawableId);

    void showSignInDialog();

    void dismissSignInDialog();

    void showCreateUserFailToast();

    void signIn();

    void startProblemReportActivity(DivisionAndHospitalViewModel viewModel);

    void startShareActivity();

    void startCommentActivity(DivisionAndHospitalViewModel viewModel, String email);

    void startAddDoctorActivity(DivisionAndHospitalViewModel viewModel);

    void sendClickFabEvent(String fabLabel);
}
