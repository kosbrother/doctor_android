package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.DoctorSearchListAdapter;
import kosbrother.com.doctorguide.adapters.HospitalSearchAdapter;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.model.SearchableModel;
import kosbrother.com.doctorguide.presenter.SearchablePresenter;
import kosbrother.com.doctorguide.view.SearchableView;

public class SearchableActivity extends BaseActivity implements SearchableView {

    public final int SEARCH_NUM = 5;

    public enum LIST_TYPE {
        MORE,
        NOT_MORE
    }

    private ActionBar actionbar;
    private ProgressDialog progressDialog;

    private SearchablePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchablePresenter(this, new SearchableModel());
        presenter.onCreate();

        if (getIntent() != null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
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
    public void setContentView() {
        setContentView(R.layout.activity_searchable);
    }

    @Override
    public void setActionBar() {
        actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("搜尋");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setActionBarTitle(String actionBarTitle) {
        actionbar.setTitle(actionBarTitle);
    }

    @Override
    public void setDoctorListView(ArrayList<Doctor> doctors) {
        ListView doctorSearchListView = (ListView) findViewById(R.id.doctorListView);
        DoctorSearchListAdapter doctorSearchAdapter;
        if (doctors.size() == SEARCH_NUM) {
            View.OnClickListener moreListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSearchMoreDoctorClick();
                }
            };
            doctorSearchAdapter = new DoctorSearchListAdapter(SearchableActivity.this, doctors, LIST_TYPE.MORE, moreListener);
        } else
            doctorSearchAdapter = new DoctorSearchListAdapter(SearchableActivity.this, doctors, LIST_TYPE.NOT_MORE);
        doctorSearchListView.setAdapter(doctorSearchAdapter);
        setDynamicHeight(doctorSearchListView);
    }

    @Override
    public void setHospitalListView(ArrayList<Hospital> hospitals) {
        ListView hospitalSearchListView = (ListView) findViewById(R.id.hospitalListView);
        HospitalSearchAdapter hospitalSearchAdapter;
        if (hospitals.size() == SEARCH_NUM) {
            View.OnClickListener moreListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSearchMoreHospitalClick();
                }
            };
            hospitalSearchAdapter = new HospitalSearchAdapter(SearchableActivity.this, hospitals, LIST_TYPE.MORE, moreListener);
        } else {
            hospitalSearchAdapter = new HospitalSearchAdapter(SearchableActivity.this, hospitals, LIST_TYPE.NOT_MORE);
        }
        hospitalSearchListView.setAdapter(hospitalSearchAdapter);
        setDynamicHeight(hospitalSearchListView);
    }

    @Override
    public void startSearchMoreHospitalActivity(String queryString) {
        Intent intent = new Intent(this, SearchMoreHospitalActivity.class);
        intent.putExtra(ExtraKey.QUERY, queryString);
        startActivity(intent);
    }

    @Override
    public void startSearchMoreDoctorActivity(String queryString) {
        Intent intent = new Intent(this, SearchMoreDoctorActivity.class);
        intent.putExtra(ExtraKey.QUERY, queryString);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            presenter.onHandleSearchIntent(query);
        }
    }

    private void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }
}
