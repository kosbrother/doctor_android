package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.DoctorSearchListAdapter;
import kosbrother.com.doctorguide.adapters.HospitalSearchAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;

public class SearchMoreActivity extends AppCompatActivity {

    private static final int SEARCH_NUM = 30;
    private String type;
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_more);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getString(ExtraKey.TYPE);
            query = extras.getString(ExtraKey.QUERY);
        }

        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("搜尋更多");
        actionbar.setDisplayHomeAsUpEnabled(true);
        new GetSearchResultTask().execute();
    }

    private class GetSearchResultTask extends AsyncTask {

        private ProgressDialog mProgressDialog;
        private ArrayList<Doctor> search_result_doctors;
        private ArrayList<Hospital> search_result_hospitals;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(SearchMoreActivity.this);
        }

        @Override
        protected Object doInBackground(Object... params) {
            if (type.equals("DOCTOR"))
                search_result_doctors = DoctorGuideApi.searchDoctors(query, SEARCH_NUM);
            else if (type.equals("HOSPITAL"))
                search_result_hospitals = DoctorGuideApi.searchHospitals(query, SEARCH_NUM);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            ListView listView = (ListView) findViewById(R.id.ListView);
            if (type.equals("DOCTOR")) {
                DoctorSearchListAdapter adapter = new DoctorSearchListAdapter(SearchMoreActivity.this, search_result_doctors, SearchableActivity.LIST_TYPE.NOT_MORE);
                listView.setAdapter(adapter);
            } else if (type.equals("HOSPITAL")) {
                HospitalSearchAdapter adapter = new HospitalSearchAdapter(SearchMoreActivity.this, search_result_hospitals, SearchableActivity.LIST_TYPE.NOT_MORE);
                listView.setAdapter(adapter);
            }
        }

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
}
