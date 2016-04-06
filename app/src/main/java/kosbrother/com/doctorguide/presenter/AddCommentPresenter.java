package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.task.GetDivisionScoreTask;
import kosbrother.com.doctorguide.task.SubmitCommentTask;
import kosbrother.com.doctorguide.view.AddCommentView;

public class AddCommentPresenter implements
        GetDivisionScoreTask.GetDivisionScoreListener,
        SubmitCommentTask.SubmitCommentListener {

    private final AddCommentView view;
    private final AddCommentModel model;

    public AddCommentPresenter(AddCommentView view, AddCommentModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setLoadingView();
        view.initActionbar();

        model.requestGetDivisions(this);
    }

    public void onStep1NextButtonClick() {
        view.setStep1CheckedAndClickable();
        view.switchStep1Info(
                model.getHospitalName(),
                model.getDivisionDoctorString(),
                model.getDateString());
        view.showStep2ViewSwitcher();
        handleDoctorCommentView();
    }

    public void onStep2NextButtonClick(AddCommentModel.DivisionScore divisionScore) {
        view.setStep2CheckedAndClickable();
        model.setDivisionScore(divisionScore);
        view.switchStep2Info(model.getDivisionScore());
        view.showStep3View();
    }

    public void onStep3NextButtonClick(String divisionComment) {
        handleStep3NextClick(divisionComment);
    }

    public void onStep3SkipButtonClick() {
        handleStep3NextClick("暫無評論");
    }

    public void onStep4NextButtonClick(AddCommentModel.DoctorScore doctorScore, boolean recommend) {
        view.setStep4CheckedAndClickable();
        model.setDoctorScore(doctorScore);
        model.setRecommend(recommend);
        view.switchStep4Info(doctorScore);
        view.showStep5SwitchView();
    }

    public void onStep5NextButtonClick(String doctorComment) {
        handleStep5NextClick(doctorComment);
    }

    public void onStep5SkipButtonClick() {
        handleStep5NextClick("暫無評論");
    }

    public void onStep1EditLayoutClick() {
        view.switchStep1Edit();
        view.hideSubmitLayout();
        view.resetStep2View();
        view.resetStep3View();
        view.resetStep4View();
        view.resetStep5View();
    }

    public void onStep2EditLayoutClick() {
        view.switchStep2();
        view.hideSubmitLayout();
        view.resetStep3View();
        view.resetStep4View();
        view.resetStep5View();
    }

    public void onStep3EditLayoutClick() {
        view.switchStep3();
        view.hideSubmitLayout();
        view.resetStep4View();
        view.resetStep5View();
    }

    public void onStep4EditLayoutClick() {
        view.switchStep4();
        view.hideSubmitLayout();
        view.resetStep5View();
    }

    public void onStep5EditLayoutClick() {
        view.switchStep5();
        view.hideSubmitLayout();
    }

    public void onSubmitButtonClick() {
        view.showProgressDialog();
        view.sendAddCommentClickSubmitButtonEvent();
        model.requestSubmitComment(this);
    }

    @Override
    public void onGetDivisionScoreResult(ArrayList<Division> divisions) {
        model.initResultData(divisions);

        view.setContentView();
        view.setHospitalNameText(model.getHospitalName());
        view.setDivisionSpinner(model.getDivisions(), model.getDivisionSelection());
        view.setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());
        view.setDateText(model.getDateString());
        view.initViewSwitcher();
        view.initButton();
        view.initEditIcon();
        view.initSlider();
        view.initEditTextListener();
        handleDoctorCommentView();
    }

    @Override
    public void onPostCommentResultSuccess() {
        view.hideProgressDialog();
        view.showPostCommentResultSuccessDialog();
    }

    public void onDateButtonClick() {
        view.showDatePickerDialog(model.getDatePickerViewModel());
    }

    public void onDateSet(int y, int m, int dayOfMonth) {
        model.updateDate(y, m, dayOfMonth);

        String dateText = model.getDateString();
        view.setDateText(dateText);
        view.sendAddCommentClickDateTextEvent(dateText);
    }

    public void onDivisionItemSelected(int position) {
        model.updateDivisionAndDoctors(position);

        view.setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());

        view.sendAddCommentClickDivisionSpinnerEvent(model.getDivisionFromPosition(position));
    }

    public void onDoctorItemSelected(int position) {
        model.updateDoctor(position);

        view.sendAddCommentClickDoctorSpinnerEvent(model.getDoctorFromPosition(position));
    }

    public void onDivisionScored() {
        model.plusDivisionScoredCount();
        if (model.divisionScoreFinish()) {
            view.enableStep2NextButton();
        }
    }

    public void onDoctorScored() {
        model.plusDoctorScoredCount();
        if (model.doctorScoreFinish()) {
            view.enableStep4NextButton();
        }
    }

    public void afterStep3CommentTextChanged(String string) {
        if (string.isEmpty()) {
            view.disableStep3NextButton();
        } else {
            view.enableStep3NextButton();
        }
    }

    public void afterDoctorCommentTextChanged(String string) {
        if (string.isEmpty()) {
            view.disableStep5NextButton();
        } else {
            view.enableStep5NextButton();
        }
    }

    protected void handleDoctorCommentView() {
        if (model.isDivisionComment()) {
            view.hideDoctorCommentView();
        } else {
            view.showDoctorCommentView();
        }
    }

    protected void handleStep3NextClick(String divisionComment) {
        model.setDivisionComment(divisionComment);
        view.setStep3CheckedAndClickable();
        view.switchStep3Info(divisionComment);
        if (model.isDivisionComment()) {
            view.showSubmitLayout();
        } else {
            view.showStep4SwitchView();
        }
    }

    protected void handleStep5NextClick(String doctorComment) {
        model.setDoctorComment(doctorComment);
        view.setStep5CheckedAndClickable();
        view.switchStep5Info(doctorComment);
        view.showSubmitLayout();
    }

}