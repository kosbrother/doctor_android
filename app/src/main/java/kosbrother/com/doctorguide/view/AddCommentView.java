package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;

public interface AddCommentView {
    void setContentView();

    void initToolbar();

    void initTabLayoutWithViewPager();

    void setHospitalNameText(String hospitalName);

    void setDateText(String date);

    void showProgressDialog();

    void hideProgressDialog();

    void setDivisionSpinner(ArrayList<String> divs, int selection);

    void setDoctorSpinner(ArrayList<String> drs, int selection);

    void sendAddCommentClickDivisionSpinnerEvent(String division);

    void sendAddCommentClickDoctorSpinnerEvent(String doctorFromPosition);

    void sendIsDirectSubmitToBroadcast(boolean isDirectSubmit);

    void sendAddCommentClickDateTextEvent(String dateSetString);

    void showDatePickerDialog(DatePickerViewModel datePickerViewModel);

    void showPostCommentResultSuccessDialog();

    void superOnBackPressed();

    void disableTabClickAndPagerSwipe();

    void enableTabClickAndPagerSwipe();

    void moveToDivisionPage();

    void moveToDoctorPage();
}
