package kosbrother.com.doctorguide.presenter;

import com.google.android.gms.maps.model.LatLng;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.OrderStrings;
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

        verify(view).setActionBar();
        verify(view).setActionBarTitle(model.getAreaName());
    }

    @Test
    public void testOnGetLocationSuccess() throws Exception {
        LatLng latLng = new LatLng(0, 0);

        presenter.onGetLocationSuccess(latLng);

        verify(model).setLatLng(latLng);
        verify(view).setContentView();
        verify(view).setOrderSpinner(model.getSortPosition(), model.getOrderStringNameArray());
        verify(view).setAreaSpinner(model.getAreaPosition(), model.getAreaStringArray());
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

    @Test // Prevent some device trigger item click event when set listener.
    public void testOnAreaItemSelected_samePosition() throws Exception {
        int position = 0;
        when(model.getAreaPosition()).thenReturn(position);

        presenter.onAreaItemSelected(position);

        verify(model, never()).setAreaPosition(position);
        verify(model, never()).resetToFirstLoad();
        String areaName = model.getAreaName();
        verify(view, never()).setActionBarTitle(areaName);
        verify(view, never()).sendAreaClickAreaSpinnerEvent(areaName);

        verify(presenter, never()).requestHospitalsWithProgressDialog();
    }

    @Test
    public void testOnAreaItemSelected_diffPosition() throws Exception {
        int position = 0;
        when(model.getAreaPosition()).thenReturn(position + 1);

        presenter.onAreaItemSelected(position);

        verify(model).setAreaPosition(position);
        verify(model).resetToFirstLoad();
        String areaName = model.getAreaName();
        verify(view).setActionBarTitle(areaName);
        verify(view).sendAreaClickAreaSpinnerEvent(areaName);

        verify(presenter).requestHospitalsWithProgressDialog();
    }

    @Test // Prevent some device trigger item click event when set listener.
    public void testOnSortItemSelected_samePosition() throws Exception {
        String[] orderStringArray = OrderStrings.getOrderStringNameArray();
        when(model.getOrderStringNameArray()).thenReturn(orderStringArray);
        int position = 0;
        when(model.getSortPosition()).thenReturn(position);

        presenter.onSortItemSelected(position);

        verify(model, never()).setSortPosition(position);
        verify(model, never()).resetToFirstLoad();
        verify(view, never()).sendAreaClickSortSpinnerEvent(model.getOrderStringNameArray()[position]);

        verify(presenter, never()).requestHospitalsWithProgressDialog();
    }

    @Test
    public void testOnSortItemSelected_diffPosition() throws Exception {
        String[] orderStringArray = OrderStrings.getOrderStringNameArray();
        when(model.getOrderStringNameArray()).thenReturn(orderStringArray);
        int position = 0;
        when(model.getSortPosition()).thenReturn(position + 1);

        presenter.onSortItemSelected(position);

        verify(model).setSortPosition(position);
        verify(model).resetToFirstLoad();
        verify(view).sendAreaClickSortSpinnerEvent(model.getOrderStringNameArray()[position]);

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
        verify(view).sendAreaClickHospitalItemEvent(hospital.name);
    }

}