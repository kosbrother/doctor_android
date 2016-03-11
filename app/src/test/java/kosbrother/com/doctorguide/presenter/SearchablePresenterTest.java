package kosbrother.com.doctorguide.presenter;

import org.junit.Before;
import org.junit.Test;

import kosbrother.com.doctorguide.model.SearchableModel;
import kosbrother.com.doctorguide.view.SearchableView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SearchablePresenterTest {

    private SearchableView view;
    private SearchableModel model;
    private SearchablePresenter presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(SearchableView.class);
        model = mock(SearchableModel.class);
        presenter = new SearchablePresenter(view, model);
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
    }

    @Test
    public void testOnHandleSearchIntent() throws Exception {
        String query = "query";

        presenter.onHandleSearchIntent(query);

        verify(model).setQueryString(query);
        verify(view).setActionBarTitle(model.getActionBarTitle());
        verify(view).showProgressDialog();
        verify(model).requestGetSearchResult(presenter);
    }

    @Test
    public void testOnGetSearchResultDone() throws Exception {
        presenter.onGetSearchResultDone();

        verify(view).hideProgressDialog();
        verify(view).setDoctorListView(model.getDoctors());
        verify(view).setHospitalListView(model.getHospitals());
    }

    @Test
    public void testOnSearchMoreHospitalClick() throws Exception {
        presenter.onSearchMoreHospitalClick();

        verify(view).startSearchMoreHospitalActivity(model.getQueryString());
    }

    @Test
    public void testOnSearchMoreDoctorClick() throws Exception {
        presenter.onSearchMoreDoctorClick();

        verify(view).startSearchMoreDoctorActivity(model.getQueryString());
    }
}