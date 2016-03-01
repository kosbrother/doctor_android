package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.DoctorSearchListAdapter;
import kosbrother.com.doctorguide.adapters.HospitalSearchAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;

public class SearchableActivity extends AppCompatActivity {

    private ActionBar actionbar;
    private final int SEARCH_NUM = 5;

    public enum LIST_TYPE {
        MORE,
        NOT_MORE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("搜尋");
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null) {
            handleIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            actionbar.setTitle("搜尋：" + query);
            new GetSearchResultTask().execute();
        }
    }

    private ArrayList<Doctor> search_result_doctors;
    private ArrayList<Hospital> search_result_hospitals;

    private String query;

    private class GetSearchResultTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(SearchableActivity.this);
        }

        @Override
        protected Object doInBackground(Object... params) {
            search_result_doctors = DoctorGuideApi.searchDoctors(query, SEARCH_NUM);
            search_result_hospitals = DoctorGuideApi.searchHospitals(query, SEARCH_NUM);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();

            setDoctorListView();
            setHospitalListView();
        }

        private void setHospitalListView() {
            ListView hospitalSearchListView = (ListView) findViewById(R.id.hospitalListView);
            HospitalSearchAdapter hospitalSearchAdapter;
            if (search_result_hospitals.size() == SEARCH_NUM) {
                View.OnClickListener moreListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchableActivity.this, SearchMoreActivity.class);
                        intent.putExtra("TYPE", "HOSPITAL");
                        intent.putExtra("QUERY", query);
                        startActivity(intent);
                    }
                };
                hospitalSearchAdapter = new HospitalSearchAdapter(SearchableActivity.this, search_result_hospitals, LIST_TYPE.MORE, moreListener);
            } else
                hospitalSearchAdapter = new HospitalSearchAdapter(SearchableActivity.this, search_result_hospitals, LIST_TYPE.NOT_MORE);

            hospitalSearchListView.setAdapter(hospitalSearchAdapter);
            ListUtils.setDynamicHeight(hospitalSearchListView);
        }

        private void setDoctorListView() {
            ListView doctorSearchListView = (ListView) findViewById(R.id.doctorListView);
            DoctorSearchListAdapter doctorSearchAdapter;
            if (search_result_doctors.size() == SEARCH_NUM) {
                View.OnClickListener moreListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchableActivity.this, SearchMoreActivity.class);
                        intent.putExtra("TYPE", "DOCTOR");
                        intent.putExtra("QUERY", query);
                        startActivity(intent);
                    }
                };
                doctorSearchAdapter = new DoctorSearchListAdapter(SearchableActivity.this, search_result_doctors, LIST_TYPE.MORE, moreListener);
            } else
                doctorSearchAdapter = new DoctorSearchListAdapter(SearchableActivity.this, search_result_doctors, LIST_TYPE.NOT_MORE);
            doctorSearchListView.setAdapter(doctorSearchAdapter);
            ListUtils.setDynamicHeight(doctorSearchListView);
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
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
}
