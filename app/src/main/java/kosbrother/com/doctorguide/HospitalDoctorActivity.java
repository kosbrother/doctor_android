package kosbrother.com.doctorguide;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.DoctorFragment;
import kosbrother.com.doctorguide.fragments.HospitalFragment;

public class HospitalDoctorActivity extends AppCompatActivity implements HospitalFragment.OnListFragmentInteractionListener,DoctorFragment.OnListFragmentInteractionListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener , LocationListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int categoryId;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_doctor);

        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("CATEGORY_NAME");
            actionbar.setTitle(title);
            categoryId = extras.getInt("CATEGORY_ID");
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setGoogleClient();
    }

    private void setGoogleClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HospitalFragment.newInstance(categoryId,getLatLng()), "醫院");
        adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.DISTANCETYPE,categoryId,getLatLng()), "醫生");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(Hospital item) {
        new GetDivisionsTask(item).execute();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLastLocation == null){
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }else{
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    public LatLng getLatLng(){
        return new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    private class GetDivisionsTask extends AsyncTask {

        private final Hospital item;
        private ArrayList<Division> divisions;

        public GetDivisionsTask(Hospital item){
            this.item = item;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Util.showProgressDialog(HospitalDoctorActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            divisions = DoctorGuideApi.getDivisionByHospitalAndCategory(item.id, categoryId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            Util.hideProgressDialog();
            if(divisions.size() > 1)
                showDivisionDialog(divisions,item);
            else{
                Intent intent = new Intent(HospitalDoctorActivity.this, DivisionActivity.class);
                intent.putExtra("DIVISION_ID",divisions.get(0).id);
                intent.putExtra("DIVISION_NAME",divisions.get(0).name);
                intent.putExtra("HOSPITAL_ID",item.id);
                intent.putExtra("HOSPITAL_GRADE",item.grade);
                intent.putExtra("HOSPITAL_NAME",item.name);
                startActivity(intent);
            }
        }

    }

    private void showDivisionDialog(final ArrayList<Division> divisions, final Hospital item) {
        List<String> strings = new ArrayList<String>();
        for (Division div : divisions)
            strings.add(div.name );
        String[] items = strings.toArray(new String[strings.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
        builder.setTitle("請選擇科別細項");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                Intent intent = new Intent(HospitalDoctorActivity.this, DivisionActivity.class);
                intent.putExtra("DIVISION_ID",divisions.get(position).id);
                intent.putExtra("DIVISION_NAME",divisions.get(position).name);
                intent.putExtra("HOSPITAL_ID",item.id);
                intent.putExtra("HOSPITAL_GRADE",item.grade);
                intent.putExtra("HOSPITAL_NAME",item.name);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        int titleDividerId = getResources().getIdentifier("titleDivider", "id", "android");
        View titleDivider = dialog.findViewById(titleDividerId);
        if (titleDivider != null)
            titleDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public void onListFragmentInteraction(View v, Doctor item) {
        Intent intent = new Intent(this, DoctorActivity.class);
        intent.putExtra("DOCTOR_ID",item.id);
        intent.putExtra("DOCTOR_NAME",item.name);
        intent.putExtra("HOSPITAL_NAME",item.hospital);
        startActivity(intent);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
