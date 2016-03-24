package kosbrother.com.doctorguide.view;

import android.net.Uri;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.viewmodel.HospitalScoreViewModel;

public interface HospitalView extends ProgressDialogView {
    void buildAppIndexClient();

    void connectAppIndexClient();

    void disConnectAppIndexClient();

    void startAppIndexApi(String hospitalName, Uri webUrl, Uri appUri);

    void endAppIndexApi(String hospitalName, Uri webUrl, Uri appUri);

    void setContentView();

    void setActionBar();

    void setHospitalImage(int hospitalImageRedId);

    void setHospitalName(String hospitalName);

    void setHeartButtonBackground(int resId);

    void setHeartButton();

    void setHospitalScore(HospitalScoreViewModel hospitalScoreViewModel);

    void setViewPager(int hospitalId, Hospital hospital);

    void sendHospitalClickCollectEvent(String hospitalName);

    void sendHospitalClickAddCommentEvent(String label);

    void showRemoveFromCollectSuccessSnackBar();

    void showAddToCollectSuccessSnackBar();

    void showDivisionInfoDialog(Division division);

    void startDivisionActivity(Division division);
}
