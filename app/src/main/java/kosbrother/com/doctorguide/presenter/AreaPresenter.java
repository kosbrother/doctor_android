package kosbrother.com.doctorguide.presenter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.AreaModel;
import kosbrother.com.doctorguide.task.GetHospitalsByAreaTask.GetHospitalsByAreaListener;
import kosbrother.com.doctorguide.view.AreaView;

public class AreaPresenter implements GetHospitalsByAreaListener {
    private final AreaView view;
    private final AreaModel model;

    public AreaPresenter(AreaView view, AreaModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setActionBar();
        view.setActionBarTitle(model.getAreaName());
    }

    public void onGetLocationSuccess(LatLng latLng) {
        model.setLatLng(latLng);
        view.setContentView();
        view.setOrderSpinner(model.getSortPosition(), model.getOrderStringNameArray());
        view.setAreaSpinner(model.getAreaPosition(), model.getAreaStringArray());

        requestHospitalsWithProgressDialog();
    }

    @Override
    public void onGetHospitalsSuccess(ArrayList<Hospital> hospitals) {
        if (model.getLoadPage() == 1) {
            onLoadFirstPage(hospitals);
        } else {
            onLoadNextPage(hospitals);
        }
        view.setRecyclerViewLoaded();
    }

    protected void onLoadFirstPage(ArrayList<Hospital> hospitals) {
        model.setHospitals(hospitals);
        model.plusLoadPage();
        view.setRecyclerView(hospitals, model.getLatLng());

        view.hideProgressDialog();
    }

    protected void onLoadNextPage(ArrayList<Hospital> hospitals) {
        if (hospitals.size() > 0) {
            model.addHospitals(hospitals);
            model.plusLoadPage();
            view.updateRecyclerView(hospitals);
        } else {
            model.setLoadCompleted();
        }
        view.hideLoadMoreLayout();
    }

    public void onAreaItemSelected(int position) {
        if (model.getAreaPosition() == position) {
            return;
        }
        model.setAreaPosition(position);
        model.resetToFirstLoad();
        String areaName = model.getAreaName();
        view.setActionBarTitle(areaName);
        view.sendAreaClickAreaSpinnerEvent(areaName);

        requestHospitalsWithProgressDialog();
    }

    public void onSortItemSelected(int position) {
        if (model.getSortPosition() == position) {
            return;
        }
        model.setSortPosition(position);
        model.resetToFirstLoad();
        view.sendAreaClickSortSpinnerEvent(model.getOrderStringNameArray()[position]);

        requestHospitalsWithProgressDialog();
    }

    public void onLoadMore() {
        if (!model.isLoadCompleted()) {
            view.showLoadMoreLayout();
            model.requestGetHospitals(this);
        }
    }

    protected void requestHospitalsWithProgressDialog() {
        view.showProgressDialog();

        model.requestGetHospitals(this);
    }

    public void onHospitalItemClick(Hospital hospital) {
        view.startHospitalActivity(hospital);
        view.sendAreaClickHospitalItemEvent(hospital.name);
    }
}
