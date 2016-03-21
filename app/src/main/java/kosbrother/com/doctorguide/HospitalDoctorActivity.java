package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GetLocation;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.DoctorFragment;
import kosbrother.com.doctorguide.fragments.HospitalFragment;
import kosbrother.com.doctorguide.model.HospitalDoctorModel;
import kosbrother.com.doctorguide.presenter.HospitalDoctorPresenter;
import kosbrother.com.doctorguide.view.HospitalDoctorView;

public class HospitalDoctorActivity extends GetLocationActivity implements
        HospitalFragment.OnListFragmentInteractionListener,
        DoctorFragment.OnListFragmentInteractionListener,
        GetLocation,
        HospitalDoctorView {

    private int categoryId = 0;
    private String actionBarTitle = "";

    private ProgressDialog mProgressDialog;

    private HospitalDoctorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
        HospitalDoctorModel model = new HospitalDoctorModel(categoryId, Build.VERSION.SDK_INT);
        presenter = new HospitalDoctorPresenter(this, model);
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_hospital_doctor);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
        actionbar.setTitle(actionBarTitle);
    }

    @Override
    public void setViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (viewPager.getAdapter() == null) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(HospitalFragment.newInstance(categoryId), "醫院");
            adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.DISTANCE_TYPE, categoryId), "醫生");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void showDivisionsDialog(final String[] divisionNameArray, final Hospital item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("請選擇科別細項");
        builder.setItems(divisionNameArray, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                presenter.onDivisionsDialogClick(position);
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
    public void startDivisionActivity(ArrayList<Division> divisions, Hospital hospital, int position) {
        Intent intent = new Intent(HospitalDoctorActivity.this, DivisionActivity.class);
        intent.putExtra(ExtraKey.DIVISION_ID, divisions.get(position).id);
        intent.putExtra(ExtraKey.DIVISION_NAME, divisions.get(position).name);
        intent.putExtra(ExtraKey.HOSPITAL_ID, hospital.id);
        intent.putExtra(ExtraKey.HOSPITAL_GRADE, hospital.grade);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, hospital.name);
        startActivity(intent);
    }

    @Override
    public void startDoctorActivity(Doctor doctor) {
        Intent intent = new Intent(this, DoctorActivity.class);
        intent.putExtra(ExtraKey.DOCTOR_ID, doctor.id);
        intent.putExtra(ExtraKey.HOSPITAL_ID, doctor.hospital_id);
        intent.putExtra(ExtraKey.DOCTOR_NAME, doctor.name);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, doctor.hospital);
        startActivity(intent);
    }

    @Override
    protected void onGetLocationSuccess(LatLng latLng) {
        presenter.onGetLocationSuccess();
    }

    public LatLng getLocation() {
        return new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    @Override
    public void onListFragmentInteraction(final Hospital hospital) {
        presenter.onListFragmentInteraction(hospital);
    }

    @Override
    public void onListFragmentInteraction(View v, Doctor doctor) {
        presenter.onListFragmentInteraction(doctor);
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                categoryId = extras.getInt(ExtraKey.CATEGORY_ID);
                actionBarTitle = extras.getString(ExtraKey.CATEGORY_NAME);
            }
        }
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
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

}
