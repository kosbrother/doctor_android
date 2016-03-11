package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.model.SearchMoreDoctorModel;
import kosbrother.com.doctorguide.view.SearchMoreDoctorView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchMoreDoctorPresenterTest {

    private SearchMoreDoctorView view;
    private SearchMoreDoctorModel model;
    private SearchMoreDoctorPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(SearchMoreDoctorView.class);
        model = mock(SearchMoreDoctorModel.class);
        presenter = new SearchMoreDoctorPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).showProgressDialog();
        verify(model).requestSearchDoctors(presenter);
    }

    @Test
    public void testOnSearchDoctorsResult() throws Exception {
        ArrayList<Doctor> doctors = new ArrayList<>();

        presenter.onSearchDoctorsResult(doctors);

        verify(view).hideProgressDialog();
        verify(view).setDoctorsListView(doctors);
    }
}