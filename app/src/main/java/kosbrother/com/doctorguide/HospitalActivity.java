package kosbrother.com.doctorguide;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.entity.realm.RealmHospital;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionListFragment;
import kosbrother.com.doctorguide.fragments.HospitalDetailFragment;

public class HospitalActivity extends AppCompatActivity implements DivisionListFragment.OnListFragmentInteractionListener{

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean collected;
    private int hospitalId;
    private String hospitalGrade;
    private String hospitalName;
    private FloatingActionMenu fab;
    private Hospital hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hospitalId = extras.getInt("HOSPITAL_ID");
            hospitalGrade = extras.getString("HOSPITAL_GRADE");
            hospitalName = extras.getString("HOSPITAL_NAME");
        }

        actionbar = getSupportActionBar();
        actionbar.setTitle("醫院資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setViews();
        setHeatButton();
        setFab();

        new SetFragmentTask().execute();
    }

    @Override
    public void onListFragmentInteraction(View view,Division division) {
        if(view.getId() == R.id.detail_button){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(HospitalActivity.this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(Category.getCategoryById(division.category_id).name);
            builder.setMessage(Category.getCategoryById(division.category_id).intro);
            builder.setPositiveButton("確定", null);
            builder.show();
        }else{
            Intent intent = new Intent(HospitalActivity.this, DivisionActivity.class);
            intent.putExtra("DIVISION_ID",division.id);
            intent.putExtra("DIVISION_NAME",division.name);
            intent.putExtra("HOSPITAL_ID",division.hospital_id);
            intent.putExtra("HOSPITAL_GRADE",division.hospital_grade);
            intent.putExtra("HOSPITAL_NAME",division.hospital_name);
            startActivity(intent);
        }
    }

    private class SetFragmentTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Util.showProgressDialog(HospitalActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            hospital = DoctorGuideApi.getHospitalInfo(hospitalId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            Util.hideProgressDialog();
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    private void setFab() {
        fab = (FloatingActionMenu) findViewById(R.id.menu2);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                int drawableId;
                if (opened) {
                    drawableId = R.mipmap.ic_close;
                } else {
                    drawableId = R.mipmap.ic_fab;
                }
                Drawable drawable = getResources().getDrawable(drawableId);
                fab.getMenuIconView().setImageDrawable(drawable);
            }
        });
        FloatingActionButton fabProblemReport = (FloatingActionButton) findViewById(R.id.fab_problem_report);
        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        FloatingActionButton fabAddDoctor = (FloatingActionButton) findViewById(R.id.fab_add_doctor);
        fabProblemReport.setOnClickListener(clickListener);
        fabShare.setOnClickListener(clickListener);
        fabAddDoctor.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fab.close(true);
            Intent intent;
            switch (v.getId()) {
                case R.id.fab_problem_report:
                    intent = new Intent(HospitalActivity.this, ProblemReportActivity.class);
                    intent.putExtra("REPORT_TYPE",getString(R.string.hospital_page));
                    intent.putExtra("HOSPITAL_NAME",hospitalName);
                    startActivity(intent);
                    break;
                case R.id.fab_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case R.id.fab_add_doctor:
                    intent = new Intent(HospitalActivity.this, AddDoctorActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

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
                            hosp.setAddress(hospital.address);
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
        adapter.addFragment(HospitalDetailFragment.newInstance(hospitalId,hospital), "關於本院");
        adapter.addFragment(DivisionListFragment.newInstance(hospitalId,hospital.divisions), "院內科別");
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
