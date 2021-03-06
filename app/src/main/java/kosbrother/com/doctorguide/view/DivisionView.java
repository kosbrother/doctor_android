package kosbrother.com.doctorguide.view;

import java.util.List;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionScoreViewModel;

public interface DivisionView extends BaseView, ProgressDialogView {
    void setContentView();

    void initActionBar();

    void setDivisionImage(int divisionImageResId);

    void setHospitalNameFromHtml(String htmlString);

    void setDivisionScoreText(DivisionScoreViewModel divisionScoreViewModel);

    void setupViewPager(int hospitalId, int divisionId);

    void setupSpinner(List<String> divisionNames, String divisionName);

    void showCancelCollectDialog(String message);

    void showCollectSuccessSnackBar();

    void updateAdapter();

    void executeCancelCollectDoctor(int doctorId);

    void executeCollectDoctor(Doctor doctor, String hospitalName, int hospitalId);

    void sendDivisionClickDivisionSpinnerEvent(String clickDivisionName);

    void sendDivisionClickHospitalTextEvent(String hospitalName);

    void sendDivisionAddDoctorClickEvent(String label);

    void sendDivisionClickAddCommentEvent(String label);

    void startDoctorActivity(Doctor doctor, DivisionActivityViewModel viewModel);

    void startDivisionActivity(DivisionActivityViewModel viewModel, int clickDivisionId, String clickDivisionName);

    void startHospitalActivity(DivisionActivityViewModel viewModel);
}
