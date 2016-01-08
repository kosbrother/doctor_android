package kosbrother.com.doctorguide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionListFragment;
import kosbrother.com.doctorguide.fragments.HospitalDetailFragment;

public class HospitalActivity extends AppCompatActivity {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean collected;
    private int hospitalId;
    private String hospitalGrade;
    private String hospitalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        actionbar = getSupportActionBar();
        actionbar.setTitle("醫院資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hospitalId = extras.getInt("HOSPITAL_ID");
            hospitalGrade = extras.getString("HOSPITAL_GRADE");
            hospitalName = extras.getString("HOSPITAL_NAME");
        }
        setViews();
        setHeatButton();
    }

    private void setViews() {
        ImageView divImage = (ImageView)findViewById(R.id.div_image);
        TextView hospital = (TextView)findViewById(R.id.hospial_name);
        switch (hospitalGrade){
            case "醫學中心":
                divImage.setImageResource(R.mipmap.ic_hospital_biggest);
                break;
            case "區域醫院":
                divImage.setImageResource(R.mipmap.ic_hospital_medium);
                break;
            case "地區醫院":
                divImage.setImageResource(R.mipmap.ic_hospital_small);
                break;
            case "診所":
                divImage.setImageResource(R.mipmap.ic_hospital_smallest);
                break;
        }
        hospital.setText(hospitalName);
    }

    private void setHeatButton() {
        final Button heart = (Button)findViewById(R.id.heart);

        final Realm realm = Realm.getInstance(getBaseContext());
        RealmHospital hosp = realm.where(RealmHospital.class).equalTo("id", hospitalId).findFirst();
        if(hosp == null) {
            collected = false;
            heart.setBackgroundResource(R.drawable.heart_white_to_red_button);
        }else{
            collected = true;
            heart.setBackgroundResource(R.drawable.heart_read_to_white_button);
        }

        heart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collected == true) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmHospital hosp = realm.where(RealmHospital.class).equalTo("id", hospitalId).findFirst();
                            hosp.removeFromRealm();
                        }
                    });

                    heart.setBackgroundResource(R.drawable.heart_white_to_red_button);
                    collected = false;
                    Snackbar snackbar = Snackbar.make(v, "取消收藏", Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.RED);
                    snackbar.show();
                } else {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmHospital hosp = realm.createObject(RealmHospital.class);
                            hosp.setId(hospitalId);
                            hosp.setGrade(hospitalGrade);
                            hosp.setName(hospitalName);
                        }
                    });

                    heart.setBackgroundResource(R.drawable.heart_read_to_white_button);
                    collected = true;
                    Snackbar snackbar = Snackbar.make(v, "成功收藏", Snackbar.LENGTH_SHORT);
                    View view = snackbar.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                    snackbar.show();
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HospitalDetailFragment(), "關於本院");
        adapter.addFragment(new DivisionListFragment(), "院內科別");
        adapter.addFragment(new CommentFragment(), "本院評論");
        viewPager.setAdapter(adapter);
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
