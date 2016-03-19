package kosbrother.com.doctorguide.presenter;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.AreaModel;
import kosbrother.com.doctorguide.view.AreaView;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AreaPresenterTest {

    private AreaView view;
    private AreaModel model;
    private AreaPresenter presenter;
    private ArrayList<Hospital> hospitals;

    @Before
    public void setUp() throws Exception {
        view = mock(AreaView.class);
        model = mock(AreaModel.class);
        presenter = spy(new AreaPresenter(view, model));

        hospitals = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception {
        hospitals = null;
    }

    @Test
    public void testOnCreate() throws Exception {
        presenter.onCreate();

        verify(view).setContentView();
        verify(view).setActionBar();
        verify(view).setActionBarTitle(model.getAreaName());
    }

    @Test
    public void testOnGetLocationSuccess() throws Exception {
        LatLng latLng = new LatLng(0, 0);

        presenter.onGetLocationSuccess(latLng);

        verify(model).setLatLng(latLng);
        verify(view).setOrderSpinner(model.getOrderSelection(), model.getOrderStringNameArray());
        verify(view).setAreaSpinner(model.getAreaSelection(), model.getAreaStringArray());
        verify(presenter).requestHospitalsWithProgressDialog();
    }

    @Test
    public void testOnGetHospitalsSuccess_loadFirstTime() throws Exception {
        when(model.getLoadPage()).thenReturn(1);

        presenter.onGetHospitalsSuccess(hospitals);
        verify(presenter).onLoadFirstPage(hospitals);

        verify(view).setRecyclerViewLoaded();
    }

    @Test
    public void testOnGetHospitalsSuccess_loadMore() throws Exception {
        when(model.getLoadPage()).thenReturn(2);

        presenter.onGetHospitalsSuccess(hospitals);
        verify(presenter).onLoadNextPage(hospitals);

        verify(view).setRecyclerViewLoaded();
    }

    @Test
    public void testOnLoadFirstPage() throws Exception {
        presenter.onLoadFirstPage(hospitals);

        verify(model).setHospitals(hospitals);
        verify(model).plusLoadPage();
        verify(view).setRecyclerView(hospitals, model.getLatLng());

        verify(view).hideProgressDialog();
    }

    @Test
    public void testOnLoadNextPage_withHospitals() throws Exception {
        hospitals.add(new Hospital());

        presenter.onLoadNextPage(hospitals);

        verify(model).addHospitals(hospitals);
        verify(model).plusLoadPage();
        verify(view).updateRecyclerView(hospitals);
        verify(view).hideLoadMoreLayout();
    }

    @Test
    public void testOnLoadNextPage_noHospitals() throws Exception {
        presenter.onLoadNextPage(hospitals);

        verify(model).setLoadCompleted();
        verify(view).hideLoadMoreLayout();
    }

    @Test
    public void testOnAreaItemSelected() throws Exception {
        int position = 0;

        presenter.onAreaItemSelected(position);

        verify(model).setAreaSelection(position);
        verify(model).resetToFirstLoad();
        verify(view).setActionBarTitle(model.getAreaName());

        verify(presenter).requestHospitalsWithProgressDialog();
    }

    @Test
    public void testOnSortItemSelected() throws Exception {
        int position = 0;

        presenter.onOrderItemSelected(position);

        verify(model).setOrderSelection(position);
        verify(model).resetToFirstLoad();

        verify(presenter).requestHospitalsWithProgressDialog();
    }

    @Test
    public void testOnLoadMore_loadCompleted() throws Exception {
        when(model.isLoadCompleted()).thenReturn(true);

        presenter.onLoadMore();

        verify(view, never()).showLoadMoreLayout();
        verify(model, never()).requestGetHospitals(presenter);
    }

    @Test
    public void testOnLoadMore_notLoadCompleted() throws Exception {
        when(model.isLoadCompleted()).thenReturn(false);

        presenter.onLoadMore();

        verify(view).showLoadMoreLayout();
        verify(model).requestGetHospitals(presenter);
    }

    @Test
    public void testRequestHospitalsWithProgressDialog() throws Exception {
        presenter.requestHospitalsWithProgressDialog();

        verify(view).showProgressDialog();
        verify(model).requestGetHospitals(presenter);
    }

    @Test
    public void testOnHospitalItemClick() throws Exception {
        Hospital hospital = new Hospital();

        presenter.onHospitalItemClick(hospital);

        verify(view).startHospitalActivity(hospital);
    }

}