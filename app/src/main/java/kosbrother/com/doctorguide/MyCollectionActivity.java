package kosbrother.com.doctorguide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.fragments.DoctorMyCollectionFragment;
import kosbrother.com.doctorguide.fragments.HospitalMyCollecionFragment;
import kosbrother.com.doctorguide.model.MyCollectionModel;
import kosbrother.com.doctorguide.presenter.MyCollectionPresenter;
import kosbrother.com.doctorguide.view.MyCollectionView;

public class MyCollectionActivity extends BaseActivity implements
        DoctorMyCollectionFragment.OnListFragmentInteractionListener,
        HospitalMyCollecionFragment.OnListFragmentInteractionListener,
        MyCollectionView {

    private ViewPagerAdapter adapter;
    private MyCollectionPresenter presenter;
    private DialogInterface.OnClickListener confirmCancelHospitalListener;
    private DialogInterface.OnClickListener confirmCancelDoctorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getInstance(getBaseContext());
        MyCollectionModel model = new MyCollectionModel(realm);
        presenter = new MyCollectionPresenter(this, model);
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_collection);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("我的收藏");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void setViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HospitalMyCollecionFragment(), "收藏醫院");
        adapter.addFragment(new DoctorMyCollectionFragment(), "收藏醫生");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showCancelHospitalCollectDialog(String message) {
        if (confirmCancelHospitalListener == null) {
            confirmCancelHospitalListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.onConfirmCancelCollectHospitalClick();
                }
            };
        }
        showCancelCollectDialog(message, confirmCancelHospitalListener);
    }

    @Override
    public void showCancelDoctorCollectDialog(String message) {
        if (confirmCancelDoctorListener == null) {
            confirmCancelDoctorListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.onConfirmCancelCollectDoctorClick();
                }
            };
        }
        showCancelCollectDialog(message, confirmCancelDoctorListener);
    }

    private void showCancelCollectDialog(String message, DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                .setTitle("取消收藏")
                .setMessage(message)
                .setPositiveButton(R.string.confirm, listener)
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startHospitalActivity(RealmHospital hospital) {
        Intent intent = new Intent(this, HospitalActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, hospital.getId());
        intent.putExtra(ExtraKey.HOSPITAL_GRADE, hospital.getGrade());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, hospital.getName());
        startActivity(intent);
    }

    @Override
    public void startDoctorActivity(RealmDoctor doctor) {
        Intent intent = new Intent(this, DoctorActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, doctor.getHospitalId());
        intent.putExtra(ExtraKey.DOCTOR_ID, doctor.getId());
        intent.putExtra(ExtraKey.DOCTOR_NAME, doctor.getName());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, doctor.getHospital());
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(View view, final RealmHospital hospital) {
        if (view.getId() == R.id.heart) {
            presenter.onHospitalHeartClick(hospital);
        } else {
            presenter.onHospitalItemClick(hospital);
        }
    }

    @Override
    public void onListFragmentInteraction(View view, final RealmDoctor doctor) {
        if (view.getId() == R.id.heart) {
            presenter.onDoctorHeartClick(doctor);
        } else {
            presenter.onDoctorItemClick(doctor);
        }
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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

}
