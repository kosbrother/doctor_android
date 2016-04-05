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
    }

    public void onStep2NextButtonClick(AddCommentModel.DivisionScore divisionScore) {
        view.setStep2CheckedAndClickable();
        model.setDivisionScore(divisionScore);
        view.switchStep2Info(model.getDivisionScore());
        view.showStep3View();
    }

    public void onStep3NextButtonClick(String divisionComment) {
        view.setStep3CheckedAndClickable();
        model.setDivisionComment(divisionComment);
        if (model.isDivisionComment()) {
            view.showSubmitLayout();
        } else {
            view.switchStep3Info(divisionComment);
            view.showStep4SwitchView();
        }
    }

    public void onStep3SkipButtonClick() {
        view.setStep3CheckedAndClickable();
        model.setDivisionComment("暫無評論");
        view.switchStep3Info(model.getDivisionComment());
        if (model.isDivisionComment()) {
            view.showSubmitLayout();
        } else {
            view.showStep4SwitchView();
        }
    }

    public void onStep4NextButtonClick(AddCommentModel.DoctorScore doctorScore, boolean recommend) {
        view.setStep4CheckedAndClickable();
        model.setDoctorScore(doctorScore);
        model.setRecommend(recommend);
        view.switchStep4Info(doctorScore);
        view.showStep5SwitchView();
    }

    public void onStep5NextButtonClick(String doctorComment) {
        view.setStep5CheckedAndClickable();
        model.setDoctorComment(doctorComment);
        view.switchStep5Info(doctorComment);
        view.showSubmitLayout();
    }

    public void onStep5SkipButtonClick() {
        view.setStep5CheckedAndClickable();
        model.setDoctorComment("暫無評論");
        view.switchStep5Info("暫無評論");
        view.showSubmitLayout();
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
        view.initSlider();
        view.initEditTextListener();
        handleDoctorCommentView();
    }

    @Override
    public void onPostCommentResultSuccess() {
        view.hideProgressDialog();
        view.showPostCommentResultSuccessDialog();
    }

    protected void handleDoctorCommentView() {
        if (model.isDivisionComment()) {
            view.hideDoctorCommentView();
            view.setDivisionSkipButtonText("略過並預覽");
            view.setDivisionNextButtonText("預覽");
        } else {
            view.showDoctorCommentView();
            view.setDivisionSkipButtonText("略過");
            view.setDivisionNextButtonText("下一步");
        }
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
        handleDoctorCommentView();
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

    public void afterDivisionCommentTextChanged(String string) {
        if (string.isEmpty()) {
            view.disableStep3NextButton();
        } else {
            view.enableStep3NextButton();
        }
    }

    public void afterDoctorCommentTextChanged(String string) {
        if (string.isEmpty()) {
            view.disableStep5FinishButton();
        } else {
            view.enableStep5FinishButton();
        }
    }
}