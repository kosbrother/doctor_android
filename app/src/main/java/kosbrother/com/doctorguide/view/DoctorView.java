package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DoctorScoreViewModel;

public interface DoctorView extends ProgressDialogView {
    void setContentView();

    void initActionBar();

    void initHeartButton();

    void setHeartButtonBackground(int backgroundResId);

    void setDoctorName(String doctorName);

    void setDoctorScore(DoctorScoreViewModel viewModel);

    void setViewPager(int doctorId);

    void showCancelCollectSnackBar();

    void showCollectSuccessSnackBar();

    void sendDoctorClickCollectEvent(String doctorName);
}
