package kosbrother.com.doctorguide;

import android.content.DialogInterface;
import android.content.Intent;
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

public class HospitalDoctorActivity extends AppCompatActivity implements HospitalFragment.OnListFragmentInteractionListener,DoctorFragment.OnListFragmentInteractionListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int categoryId;

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
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HospitalFragment.newInstance(categoryId), "醫院");
        adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.DISTANCETYPE,categoryId), "醫生");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(Hospital item) {
        new GetDivisionsTask(item).execute();
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
    public void onListFragmentInteraction(Doctor item) {
        Intent intent = new Intent(this, DoctorActivity.class);
        intent.putExtra("DOCTOR_ID",item.id);
        intent.putExtra("DOCTOR_NAME",item.name);
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
