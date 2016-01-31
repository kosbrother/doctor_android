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
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.fragments.DoctorMyCollectionFragment;
import kosbrother.com.doctorguide.fragments.HospitalMyCollecionFragment;

public class MyCollectionActivity extends AppCompatActivity implements DoctorMyCollectionFragment.OnListFragmentInteractionListener, HospitalMyCollecionFragment.OnListFragmentInteractionListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        actionbar = getSupportActionBar();
        actionbar.setTitle("我的收藏");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HospitalMyCollecionFragment(), "收藏醫院");
        adapter.addFragment(new DoctorMyCollectionFragment(), "收藏醫生");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(View view, final RealmHospital item) {
        if(view.getId() == R.id.heart){
            String message = "確定要取消收藏「" + item.getName() + "」嗎？";

            new AlertDialog.Builder(MyCollectionActivity.this,R.style.AppCompatAlertDialogStyle)
                    .setTitle("取消收藏")
                    .setMessage(message)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Realm realm = Realm.getInstance(getBaseContext());
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmHospital hosp = realm.where(RealmHospital.class).equalTo("id", item.getId()).findFirst();
                                    hosp.removeFromRealm();
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }else{
            Intent intent = new Intent(this, HospitalActivity.class);
            intent.putExtra("HOSPITAL_ID",item.getId());
            intent.putExtra("HOSPITAL_GRADE",item.getGrade());
            intent.putExtra("HOSPITAL_NAME",item.getName());
            startActivity(intent);
        }

    }

    @Override
    public void onListFragmentInteraction(View view, final RealmDoctor item) {
        if(view.getId() == R.id.heart){
            String message = "確定要取消收藏「" + item.getName() + " 醫師" +"」嗎？";

            new AlertDialog.Builder(MyCollectionActivity.this,R.style.AppCompatAlertDialogStyle)
                    .setTitle("取消收藏")
                    .setMessage(message)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Realm realm = Realm.getInstance(getBaseContext());
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    RealmDoctor doc = realm.where(RealmDoctor.class).equalTo("id", item.getId()).findFirst();
                                    doc.removeFromRealm();
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }else{
            Intent intent = new Intent(this, DoctorActivity.class);
            intent.putExtra("HOSPITAL_ID",item.getHospitalId());
            intent.putExtra("DOCTOR_ID",item.getId());
            intent.putExtra("DOCTOR_NAME",item.getName());
            intent.putExtra("HOSPITAL_NAME",item.getHospital());
            startActivity(intent);
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
        public int getItemPosition(Object object){
            return POSITION_NONE;
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
