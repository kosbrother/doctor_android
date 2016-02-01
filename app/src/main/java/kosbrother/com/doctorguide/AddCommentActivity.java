package kosbrother.com.doctorguide;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.app.ProgressDialog;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.custom.CustomViewPager;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.fragments.AddDivisionCommentFragment;
import kosbrother.com.doctorguide.fragments.AddDoctorCommentFragment;

public class AddCommentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private int year;
    private int month;
    private int day;
    private ArrayList<Division> divisions;
    private int hospitalId;
    private int divisionId;
    private String hospitalName;
    private int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        actionbar = getSupportActionBar();
        actionbar.setTitle("新增評論");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hospitalId = extras.getInt("HOSPITAL_ID");
            divisionId = extras.getInt("DIVISION_ID");
            hospitalName = extras.getString("HOSPITAL_NAME");
            doctorId = extras.getInt("DOCTOR_ID");
        }

        TextView hospital = (TextView)findViewById(R.id.hospial_name);
        hospital.setText(hospitalName);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        enablePagerSlide();
        setTime();

        new GetDivisionScoreTask().execute();
    }

    private class GetDivisionScoreTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(AddCommentActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            divisions = DoctorGuideApi.getDivisionsWithDoctorsByHospital(hospitalId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            setSpinner();
        }

    }

    private void setTime() {
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DAY_OF_MONTH);
        String date = year + "年" + (month)+ "月" + day + "日";
        ((TextView)findViewById(R.id.date)).setText(date);
    }

    private void setSpinner() {
        setDivisionSpinner();
        setDoctorSpinner();
    }

    private void setDivisionSpinner() {
        ArrayList<String> divs = new ArrayList<>();
        final ArrayList<Integer> divsValue = new ArrayList<>();
        for(Division d : divisions){
            divs.add(d.name);
            divsValue.add(d.id);
        }
        Spinner div_spinner = (Spinner)findViewById(R.id.div_selector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, divs.toArray(new String[divs.size()]));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        div_spinner.setAdapter(adapter);
        div_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                divisionId = divsValue.get(position);
                setDoctorSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(divisionId == 0)
            divisionId = getDivisionIdFromDoctors(divisions);

        div_spinner.setSelection(divsValue.indexOf(divisionId));
    }

    private int getDivisionIdFromDoctors(ArrayList<Division> divisions) {
        for(Division d : divisions){
            for(Doctor dr: d.doctors){
                if(dr.id == doctorId)
                    return d.id;
            }
        }
        return 0;
    }

    private void setDoctorSpinner(){
        Division division = getDivision(divisionId);
        ArrayList<String> drs = new ArrayList<>();
        ArrayList<Integer> drsValue = new ArrayList<>();
        drs.add("未指定醫師");
        drsValue.add(0);
        if(division.doctors != null)
            for(Doctor dr : division.doctors){
                drs.add(dr.name);
                drsValue.add(dr.id);
            }

        Spinner dr_spinner = (Spinner)findViewById(R.id.dr_selector);
        ArrayAdapter<String> dr_adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, drs.toArray(new String[drs.size()]));
        dr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dr_spinner.setAdapter(dr_adapter);

        if(doctorId != 0)
            dr_spinner.setSelection(drsValue.indexOf(doctorId));
    }

    public Division getDivision(int id)
    {
        for (int i = 0; i < divisions.size(); i++)
        {
            Division d = divisions.get(i);
            if ( id == d.id)
            {
                return d;
            }
        }

        return null;
    }

    private void enablePagerSlide() {
        viewPager.setPagingEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddDivisionCommentFragment(), "科別評論");
        adapter.addFragment(new AddDoctorCommentFragment(), "醫師評論");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int y, int m, int dayOfMonth) {
        year = y;
        month = m + 1;
        day = dayOfMonth;
        String date = year + "年" + (month)+ "月" + day + "日";
        ((TextView)findViewById(R.id.date)).setText(date);
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

    public void dateClick(View v) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                AddCommentActivity.this,
                year,month -1 ,day
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }
}
