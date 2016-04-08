package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;

public interface AddCommentView extends ProgressDialogView {
    void setLoadingView();

    void setContentView();

    void initActionbar();

    void setHospitalNameText(String hospitalName);

    void setDateText(String dateString);

    void setDivisionSpinner(ArrayList<String> divisions, int divisionSelection);

    void setDoctorSpinner(ArrayList<String> doctors, int doctorSelection);

    void initViewSwitcher();

    void initButton();

    void initEditIcon();

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

    void hideSubmitLayout();

    void resetStep2View();

    void resetStep3View();

    void resetStep4View();

    void resetStep5View();

    void showPostCommentResultSuccessDialog();

    void showDatePickerDialog(DatePickerViewModel datePickerViewModel);

    void enableStep2NextButton();

    void enableStep4NextButton();

    void disableStep3NextButton();

    void enableStep3NextButton();

    void disableStep5NextButton();

    void enableStep5NextButton();

    void setStep1CheckedAndClickable();

    void setStep2CheckedAndClickable();

    void setStep3CheckedAndClickable();

    void setStep4CheckedAndClickable();

    void setStep5CheckedAndClickable();

    void switchStep1Edit();

    void switchStep2();

    void switchStep3();

    void switchStep4();

    void switchStep5();

    void sendAddCommentClickDateTextEvent(String dateText);

    void sendAddCommentClickDivisionSpinnerEvent(String divisionFromPosition);

    void sendAddCommentClickDoctorSpinnerEvent(String doctorFromPosition);

    void sendAddCommentClickSubmitButtonEvent();

}
