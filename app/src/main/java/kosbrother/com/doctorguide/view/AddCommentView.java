package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;

public interface AddCommentView extends ProgressDialogView{
    void setLoadingView();

    void setContentView();

    void initActionbar();

    void setHospitalNameText(String hospitalName);

    void setDateText(String dateString);

    void setDivisionSpinner(ArrayList<String> divisions, int divisionSelection);

    void setDoctorSpinner(ArrayList<String> doctors, int doctorSelection);

    void initViewSwitcher();

    void initButton();

    void initSlider();

    void initEditTextListener();

    void hideDoctorCommentView();

    void showDoctorCommentView();

    void switchStep1Info(String hospitalName, String divisionDoctorString, String dateString);

    void showStep2ViewSwitcher();

    void switchStep2Info(AddCommentModel.DivisionScore divisionScore);

    void showStep3View();

    void switchStep3Info(String divisionComment);

    void showStep4SwitchView();

    void switchStep4Info(AddCommentModel.DoctorScore doctorScore);

    void showStep5SwitchView();

    void switchStep5Info(String doctorComment);

    void showSubmitLayout();

    void showPostCommentResultSuccessDialog();

    void setDivisionSkipButtonText(String text);

    void setDivisionNextButtonText(String text);

    void showDatePickerDialog(DatePickerViewModel datePickerViewModel);

    void enableStep2NextButton();

    void enableStep4NextButton();

    void disableStep3NextButton();

    void enableStep3NextButton();

    void disableStep5FinishButton();

    void enableStep5FinishButton();

    void setStep1CheckedAndClickable();

    void setStep2CheckedAndClickable();

    void setStep3CheckedAndClickable();

    void setStep4CheckedAndClickable();

    void setStep5CheckedAndClickable();

    void sendAddCommentClickDateTextEvent(String dateText);

    void sendAddCommentClickDivisionSpinnerEvent(String divisionFromPosition);

    void sendAddCommentClickDoctorSpinnerEvent(String doctorFromPosition);

    void sendAddCommentClickSubmitButtonEvent();
}
