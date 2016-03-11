package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.SearchMoreHospitalModel;
import kosbrother.com.doctorguide.view.SearchMoreHospitalView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchMoreHospitalPresenterTest {

    private SearchMoreHospitalView view;
    private SearchMoreHospitalModel model;
    private SearchMoreHospitalPresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(SearchMoreHospitalView.class);
        model = mock(SearchMoreHospitalModel.class);
        presenter = new SearchMoreHospitalPresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).showProgressDialog();
        verify(model).requestSearchHospital(presenter);
    }

    @Test
    public void testOnSearchHospitalsResult() throws Exception {
        ArrayList<Hospital> hospitals = new ArrayList<>();

        presenter.onSearchHospitalsResult(hospitals);

        verify(view).hideProgressDialog();
        verify(view).setHospitalsListView(hospitals);
    }
}