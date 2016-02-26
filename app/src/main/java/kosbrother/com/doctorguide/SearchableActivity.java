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
            search_result_doctors = DoctorGuideApi.searchDoctors(query);
            search_result_hospitals = DoctorGuideApi.searchHospitals(query);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            ListView doctorSearchListView = (ListView) findViewById(R.id.doctorListView);
            ListView hospitalSearchListView = (ListView) findViewById(R.id.hospitalListView);
            DoctorSearchListAdapter doctorSearchAdapter = new DoctorSearchListAdapter(SearchableActivity.this, search_result_doctors);
            HospitalSearchAdapter hospitalSearchAdapter = new HospitalSearchAdapter(SearchableActivity.this, search_result_hospitals);


            doctorSearchListView.setAdapter(doctorSearchAdapter);

            hospitalSearchListView.setAdapter(hospitalSearchAdapter);

            ListUtils.setDynamicHeight(doctorSearchListView);
            ListUtils.setDynamicHeight(hospitalSearchListView);
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
