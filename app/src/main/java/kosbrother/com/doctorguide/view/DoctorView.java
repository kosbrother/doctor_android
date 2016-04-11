package kosbrother.com.doctorguide.view;

import android.net.Uri;

import kosbrother.com.doctorguide.viewmodel.DoctorScoreViewModel;

public interface DoctorView extends ProgressDialogView {
    void buildAppIndexClient();

    void connectAppIndexClient();

    void disConnectAppIndexClient();

    void startAppIndexApi(String doctorName, Uri webUrl, Uri appUri);

    void endAppIndexApi(String doctorName, Uri webUrl, Uri appUri);

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

    void sendDoctorClickAddCommentEvent(String label);
}
