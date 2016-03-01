package kosbrother.com.doctorguide.presenter;

import junit.framework.TestCase;

import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.model.AddCommentModel;
import kosbrother.com.doctorguide.view.AddCommentView;

import static org.mockito.Mockito.verify;

public class AddCommentPresenterTest extends TestCase {

    private AddCommentView view;
    private AddCommentModel model;
    private AddCommentPresenter presenter;

    public void setUp() throws Exception {
        super.setUp();
        view = Mockito.mock(AddCommentView.class);
        model = Mockito.mock(AddCommentModel.class);
        presenter = Mockito.spy(new AddCommentPresenter(view, model));
    }

    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).initToolbar();
        verify(view).initTabLayoutWithViewPager();
        verify(view).disableTabClickAndPagerSwipe();

        verify(view).setHospitalNameText(model.getHospitalName());
        verify(view).setDateText(model.getDateString());

        verify(view).showProgressDialog();
        verify(model).requestGetDivisions(presenter);
    }

    public void testOnGetDivisionScoreResult() throws Exception {
        ArrayList<Division> divisions = new ArrayList<>();

        presenter.onGetDivisionScoreResult(divisions);

        verify(model).initResultData(divisions);
        verify(view).hideProgressDialog();
        verify(view).setDivisionSpinner(model.getDivisions(), model.getDivisionSelection());
        verify(view).setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());
        verify(view).sendIsDirectSubmitToBroadcast(model.isDirectSubmit());
    }

    public void testOnDivisionItemSelected() throws Exception {
        int position = 0;

        presenter.onDivisionItemSelected(position);

        verify(model).updateDivisionAndDoctors(position);
        verify(view).setDoctorSpinner(model.getDoctors(), model.getDoctorSelection());
        verify(view).sendAddCommentClickDivisionSpinnerEvent(model.getDivisionFromPosition(position));
    }

    public void testOnDoctorItemSelected() throws Exception {
        int position = 0;

        presenter.onDoctorItemSelected(position);

        verify(model).updateDoctor(position);
        verify(view).sendAddCommentClickDoctorSpinnerEvent(model.getDoctorFromPosition(position));
        verify(view).sendIsDirectSubmitToBroadcast(model.isDirectSubmit());
    }

    public void testOnPassParams() throws Exception {
        HashMap<String, String> map = new HashMap<>();

        presenter.onPassParams(map);

        verify(model).putSubmitParams(map);
    }

    public void testOnDateClick() throws Exception {
        presenter.onDateButtonClick();

        verify(view).showDatePickerDialog(model.getDatePickerViewModel());
    }

    public void testOnDateSet() throws Exception {
        int y = 0;
        int m = 0;
        int d = 0;

        presenter.onDateSet(y, m, d);

        String dateString = model.getDateString();
        verify(view).setDateText(dateString);
        verify(view).sendAddCommentClickDateTextEvent(dateString);
    }

    public void testOnSubmitPostClick() throws Exception {
        presenter.onSubmitClick();

        verify(view).showProgressDialog();
        verify(model).requestSubmitComment(presenter);
    }

    public void testOnPostCommentResultSuccess() throws Exception {
        presenter.onPostCommentResultSuccess();

        verify(view).hideProgressDialog();
        verify(view).showPostCommentResultSuccessDialog();
    }

    public void testOnBackPressed_divisionPage() throws Exception {
        presenter.onBackPressed(false);

        verify(view).superOnBackPressed();
    }

    public void testOnBackPressed_doctorPage() throws Exception {
        presenter.onBackPressed(true);

        verify(view).moveToDivisionPage();
    }

    public void testOnHomeItemSelected() throws Exception {
        presenter.onHomeItemSelected();

        verify(view).finish();
    }

    public void testName() throws Exception {
        presenter.onDivisionNextClick();

        verify(view).enableTabClickAndPagerSwipe();
        verify(view).moveToDoctorPage();
    }
}