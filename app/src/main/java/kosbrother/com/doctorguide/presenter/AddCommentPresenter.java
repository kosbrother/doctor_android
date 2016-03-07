package kosbrother.com.doctorguide.presenter;

import java.util.ArrayList;
import java.util.HashMap;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.task.GetDivisionScoreTask;
import kosbrother.com.doctorguide.task.SubmitCommentTask;
import kosbrother.com.doctorguide.view.AddCommentView;

public class AddCommentPresenter implements
        GetDivisionScoreTask.GetDivisionScoreListener,
        SubmitCommentTask.SubmitCommentListener {

    private AddCommentView view;
    private AddCommentModel model;

    public AddCommentPresenter(AddCommentView view, AddCommentModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.initToolbar();
        view.initTabLayoutWithViewPager();
        view.disableTabClickAndPagerSwipe();

        view.setHospitalNameText(model.getHospitalName());
        view.setDateText(model.getDateString());

        view.showProgressDialog();
        model.requestGetDivisions(this);
    }

    @Override
    public void onGetDivisionScoreResult(ArrayList<Division> divisions) {
        model.initResultData(divisions);

        view.hideProgressDialog();
        view.setDivisionSpinner(model.getDivisions(), model.getDivisionSelection());
        view.setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());
        view.sendIsDirectSubmitToBroadcast(model.isDirectSubmit());
    }

    public void onDivisionItemSelected(int position) {
        model.updateDivisionAndDoctors(position);

        view.setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());

        view.sendAddCommentClickDivisionSpinnerEvent(model.getDivisionFromPosition(position));
    }

    public void onDoctorItemSelected(int position) {
        model.updateDoctor(position);

        view.sendAddCommentClickDoctorSpinnerEvent(model.getDoctorFromPosition(position));
        view.sendIsDirectSubmitToBroadcast(model.isDirectSubmit());
    }

    public void onPassParams(HashMap<String, String> map) {
        model.putSubmitParams(map);
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

    public void onSubmitClick() {
        view.showProgressDialog();
        model.requestSubmitComment(this);
    }

    @Override
    public void onPostCommentResultSuccess() {
        view.hideProgressDialog();
        view.showPostCommentResultSuccessDialog();
    }

    public void onBackPressed(boolean isDoctorPage) {
        if (isDoctorPage) {
            view.moveToDivisionPage();
        } else {
            view.superOnBackPressed();
        }
    }

    public void onDivisionNextClick() {
        view.enableTabClickAndPagerSwipe();
        view.moveToDoctorPage();
    }
}
