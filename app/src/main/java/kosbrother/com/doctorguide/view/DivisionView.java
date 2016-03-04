package kosbrother.com.doctorguide.view;

import java.util.List;

import kosbrother.com.doctorguide.viewmodel.DivisionScoreViewModel;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public interface DivisionView extends ProgressDialogView {
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

    void startDoctorActivity(Doctor doctor, DivisionAndHospitalViewModel viewModel);

    void startDivisionActivity(DivisionAndHospitalViewModel viewModel, int clickDivisionId, String clickDivisionName);

    void startHospitalActivity(DivisionAndHospitalViewModel viewModel);

    void finish();
}
