package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyHospitalRecyclerViewAdapter;
import kosbrother.com.doctorguide.custom.LoadMoreRecyclerView;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.HospitalFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.area.AreaClickAreaSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.area.AreaClickHospitalListEvent;
import kosbrother.com.doctorguide.google_analytics.event.area.AreaClickSortSpinnerEvent;
import kosbrother.com.doctorguide.model.AreaModel;
import kosbrother.com.doctorguide.presenter.AreaPresenter;
import kosbrother.com.doctorguide.view.AreaView;

public class AreaActivity extends GetLocationActivity implements
        AreaView,
        OnItemSelectedListener,
        HospitalFragment.OnListFragmentInteractionListener {

    private AreaPresenter presenter;
    private ProgressDialog progressDialog;
    private LoadMoreRecyclerView recyclerView;
    private MyHospitalRecyclerViewAdapter hospitalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AreaPresenter(this, new AreaModel(getAreaSelection()));
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_area);
    }

    @Override
    public void setActionBar() {
        assert getSupportActionBar() != null;
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void setActionBarTitle(String title) {
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onGetLocationSuccess(LatLng latLng) {
        presenter.onGetLocationSuccess(latLng);
    }

    @Override
    public void setOrderSpinner(int sortPosition, String[] orderStringArray) {
        Spinner spinner = (Spinner) findViewById(R.id.sort);
        setSpinner(sortPosition, orderStringArray, spinner);
    }

    @Override
    public void setAreaSpinner(int areaPosition, String[] areaStringArray) {
        Spinner spinner = (Spinner) findViewById(R.id.area);
        setSpinner(areaPosition, areaStringArray, spinner);
    }

    private void setSpinner(int position, String[] stringArray, Spinner spinner) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_area_item,
                stringArray);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(position, false);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void setRecyclerView(ArrayList<Hospital> hospitals, LatLng latLng) {
        recyclerView = (LoadMoreRecyclerView) findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.onLoadMore();
            }
        });
        hospitalAdapter = new MyHospitalRecyclerViewAdapter(hospitals, this, latLng);
        recyclerView.setAdapter(hospitalAdapter);
    }

    @Override
    public void hideLoadMoreLayout() {
        findViewById(R.id.load_more).setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreLayout() {
        findViewById(R.id.load_more).setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecyclerViewLoaded() {
        recyclerView.setLoaded();
    }

    @Override
    public void updateRecyclerView(ArrayList<Hospital> hospitals) {
        hospitalAdapter.addHospitals(hospitals);
        hospitalAdapter.notifyDataSetChanged();
    }

    @Override
    public void startHospitalActivity(Hospital hospital) {
        Intent intent = new Intent(this, HospitalActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, hospital.id);
        intent.putExtra(ExtraKey.HOSPITAL_GRADE, hospital.grade);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, hospital.name);
        startActivity(intent);
    }

    @Override
    public void sendAreaClickAreaSpinnerEvent(String areaName) {
        GAManager.sendEvent(new AreaClickAreaSpinnerEvent(areaName));
    }

    @Override
    public void sendAreaClickSortSpinnerEvent(String sortString) {
        GAManager.sendEvent(new AreaClickSortSpinnerEvent(sortString));
    }

    @Override
    public void sendAreaClickHospitalItemEvent(String hospitalName) {
        GAManager.sendEvent(new AreaClickHospitalListEvent(hospitalName));
    }

    @Override
    public void showProgressDialog() {
        progressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.area:
                presenter.onAreaItemSelected(position);
                break;
            case R.id.sort:
                presenter.onSortItemSelected(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onListFragmentInteraction(Hospital hospital) {
        presenter.onHospitalItemClick(hospital);
    }

    private int getAreaSelection() {
        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                return extras.getInt(ExtraKey.INT_AREA_POSITION);
            }
        }
        return 0;
    }
}
