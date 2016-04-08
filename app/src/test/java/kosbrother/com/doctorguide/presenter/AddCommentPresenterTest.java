package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.view.AddCommentView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddCommentPresenterTest {

    private AddCommentView view;
    private AddCommentModel model;
    private AddCommentPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(AddCommentView.class);
        model = mock(AddCommentModel.class);
        presenter = spy(new AddCommentPresenter(view, model));
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setLoadingView();
        verify(view).initActionbar();
        verify(model).requestGetDivisions(presenter);
    }


    @Test
    public void testOnStep1NextButtonClick() throws Exception {
        presenter.onStep1NextButtonClick();

        verify(view).setStep1CheckedAndClickable();
        verify(view).switchStep1Info(
                model.getHospitalName(),
                model.getDivisionDoctorString(),
                model.getDateString());
        verify(view).showStep2ViewSwitcher();
        verify(presenter).handleDoctorCommentView();
    }

    @Test
    public void testOnStep2NextButtonClick() throws Exception {
        AddCommentModel.DivisionScore divisionScore = new AddCommentModel.DivisionScore();

        presenter.onStep2NextButtonClick(divisionScore);

        view.setStep2CheckedAndClickable();
        verify(model).setDivisionScore(divisionScore);
        verify(view).switchStep2Info(model.getDivisionScore());
        verify(view).showStep3View();
    }

    @Test
    public void testOnStep3NextButtonClick() throws Exception {
        String divisionComment = "";

        presenter.onStep3NextButtonClick(divisionComment);

        verify(presenter).handleStep3NextClick(divisionComment);
    }

    @Test
    public void testOnStep3SkipButtonClick() throws Exception {
        presenter.onStep3SkipButtonClick();

        verify(presenter).handleStep3NextClick("暫無評論");
    }

    @Test
    public void testOnStep4NextButtonClick() throws Exception {
        AddCommentModel.DoctorScore doctorScore = new AddCommentModel.DoctorScore();

        presenter.onStep4NextButtonClick(doctorScore, false);

        verify(view).setStep4CheckedAndClickable();
        verify(model).setDoctorScore(doctorScore);
        verify(model).setRecommend(false);
        verify(view).switchStep4Info(doctorScore);
        verify(view).showStep5SwitchView();
    }

    @Test
    public void testOnStep5NextButtonClick() throws Exception {
        String doctorComment = "";

        presenter.onStep5NextButtonClick(doctorComment);

        verify(presenter).handleStep5NextClick(doctorComment);
    }

    @Test
    public void testOnStep5SkipButtonClick() throws Exception {
        presenter.onStep5SkipButtonClick();

        verify(presenter).handleStep5NextClick("暫無評論");
    }

    @Test
    public void testOnStep1EditLayoutClick() {
        presenter.onStep1EditLayoutClick();

        verify(view).switchStep1Edit();
        verify(view).hideSubmitLayout();
        verify(view).resetStep2View();
        verify(view).resetStep3View();
        verify(view).resetStep4View();
        verify(view).resetStep5View();
    }

    @Test
    public void testOnStep2EditLayoutClick() {
        presenter.onStep2EditLayoutClick();

        verify(view).switchStep2();
        verify(view).hideSubmitLayout();
        verify(view).resetStep3View();
        verify(view).resetStep4View();
        verify(view).resetStep5View();
    }

    @Test
    public void testOnStep3EditLayoutClick() {
        presenter.onStep3EditLayoutClick();

        verify(view).switchStep3();
        verify(view).hideSubmitLayout();
        verify(view).resetStep4View();
        verify(view).resetStep5View();
    }

    @Test
    public void testOnStep4EditLayoutClick() {
        presenter.onStep4EditLayoutClick();

        verify(view).switchStep4();
        verify(view).hideSubmitLayout();
        verify(view).resetStep5View();
    }

    @Test
    public void testOnStep5EditLayoutClick() {
        presenter.onStep5EditLayoutClick();

        verify(view).switchStep5();
        verify(view).hideSubmitLayout();
    }

    @Test
    public void testOnSubmitButtonClick() throws Exception {
        presenter.onSubmitButtonClick();

        verify(view).showProgressDialog();
        verify(view).sendAddCommentClickSubmitButtonEvent();
        verify(model).requestSubmitComment(presenter);
    }

    @Test
    public void testOnGetDivisionScoreResult() throws Exception {
        ArrayList<Division> divisions = new ArrayList<>();

        presenter.onGetDivisionScoreResult(divisions);

        verify(view).setContentView();
        verify(view).setHospitalNameText(model.getHospitalName());
        verify(view).setDivisionSpinner(model.getDivisions(), model.getDivisionSelection());
        verify(view).setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());
        verify(view).setDateText(model.getDateString());
        verify(view).initViewSwitcher();
        verify(view).initButton();
        verify(view).initSlider();
        verify(view).initEditIcon();
        verify(view).initEditTextListener();
        verify(presenter).handleDoctorCommentView();
    }

    @Test
    public void testOnPostCommentResultSuccess() throws Exception {
        presenter.onPostCommentResultSuccess();

        verify(view).hideProgressDialog();
        verify(view).showPostCommentResultSuccessDialog();
    }

    @Test
    public void testOnDateButtonClick() throws Exception {
        presenter.onDateButtonClick();

        verify(view).showDatePickerDialog(model.getDatePickerViewModel());
    }

    @Test
    public void testOnDateSet() throws Exception {
        int y = 2016;
        int m = 4;
        int d = 5;

        presenter.onDateSet(y, m, d);

        verify(model).updateDate(y, m, d);
        verify(view).setDateText(model.getDateString());
        verify(view).sendAddCommentClickDateTextEvent(model.getDateString());
    }

    @Test
    public void testOnDivisionItemSelected() throws Exception {
        int position = 0;

        presenter.onDivisionItemSelected(position);

        verify(model).updateDivisionAndDoctors(position);
        verify(view).setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());
        verify(view).sendAddCommentClickDivisionSpinnerEvent(model.getDivisionFromPosition(position));
    }

    @Test
    public void testOnDoctorItemSelected() throws Exception {
        int position = 0;

        presenter.onDoctorItemSelected(position);

        verify(model).updateDoctor(position);
        verify(view).sendAddCommentClickDoctorSpinnerEvent(model.getDoctorFromPosition(position));
    }

    @Test
    public void testOnDivisionScored_scoreFinish() throws Exception {
        when(model.divisionScoreFinish()).thenReturn(true);

        presenter.onDivisionScored();

        verify(view).enableStep2NextButton();
    }

    @Test
    public void testOnDoctorScored_scoreFinish() throws Exception {
        when(model.doctorScoreFinish()).thenReturn(true);

        presenter.onDoctorScored();

        verify(view).enableStep4NextButton();
    }

    @Test
    public void testAfterStep3CommentTextChanged_emptyComment() throws Exception {
        String emptyComment = "";

        presenter.afterStep3CommentTextChanged(emptyComment);

        verify(view).disableStep3NextButton();
    }

    @Test
    public void testAfterStep3CommentTextChanged_withComment() throws Exception {
        String comment = "comment";

        presenter.afterStep3CommentTextChanged(comment);

        verify(view).enableStep3NextButton();
    }

    @Test
    public void testAfterStep5CommentTextChanged_emptyComment() throws Exception {
        String emptyComment = "";

        presenter.afterDoctorCommentTextChanged(emptyComment);

        verify(view).disableStep5NextButton();
    }

    @Test
    public void testAfterStep5CommentTextChanged_withComment() throws Exception {
        String comment = "comment";

        presenter.afterDoctorCommentTextChanged(comment);

        verify(view).enableStep5NextButton();
    }

    @Test
    public void testHandleDoctorCommentView_divisionComment() throws Exception {
        when(model.isDivisionComment()).thenReturn(true);

        presenter.handleDoctorCommentView();

        verify(view).hideDoctorCommentView();
    }

    @Test
    public void testHandleDoctorCommentView_doctorComment() throws Exception {
        when(model.isDivisionComment()).thenReturn(false);

        presenter.handleDoctorCommentView();

        verify(view).showDoctorCommentView();
    }

    @Test
    public void testHandleStep3NextClick_divisionComment() throws Exception {
        when(model.isDivisionComment()).thenReturn(true);
        String comment = "comment";

        presenter.handleStep3NextClick(comment);

        verify(model).setDivisionComment(comment);
        verify(view).setStep3CheckedAndClickable();
        verify(view).switchStep3Info(comment);
        verify(view).showSubmitLayout();
    }

    @Test
    public void testHandleStep3NextClick_doctorComment() throws Exception {
        when(model.isDivisionComment()).thenReturn(false);
        String comment = "comment";

        presenter.handleStep3NextClick(comment);

        verify(model).setDivisionComment(comment);
        verify(view).setStep3CheckedAndClickable();
        verify(view).switchStep3Info(comment);
        verify(view).showStep4SwitchView();
    }

    @Test
    public void testHandleStep5NextClick() throws Exception {
        String comment = "comment";

        presenter.handleStep5NextClick(comment);

        verify(model).setDoctorComment(comment);
        verify(view).setStep5CheckedAndClickable();
        verify(view).switchStep5Info(comment);
        verify(view).showSubmitLayout();
    }

}