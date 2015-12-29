package kosbrother.com.doctorguide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionScoreFragment;
import kosbrother.com.doctorguide.fragments.DoctorFragment;
import kosbrother.com.doctorguide.fragments.dummy.DummyContent;

public class DivisionActivity extends AppCompatActivity implements DoctorFragment.OnListFragmentInteractionListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        actionbar = getSupportActionBar();
        actionbar.setTitle("科別資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        setSpinner();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.HEARTTYPE), "科內醫生");
        adapter.addFragment(new DivisionScoreFragment(), "本科評分");
        adapter.addFragment(new CommentFragment(), "本科評論");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

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

    private void setSpinner() {
        Spinner spinner = (Spinner)findViewById(R.id.division_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.areas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
