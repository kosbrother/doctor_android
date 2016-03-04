package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kosbrother.com.doctorguide.Util.PassParamsToActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.custom.CustomViewPager;
import kosbrother.com.doctorguide.fragments.AddDivisionCommentFragment;
import kosbrother.com.doctorguide.fragments.AddDoctorCommentFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentClickDateTextEvent;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentClickDivisionSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.AddCommentClickDoctorSpinnerEvent;
import kosbrother.com.doctorguide.model.AddCommentModelImpl;
import kosbrother.com.doctorguide.presenter.AddCommentPresenter;
import kosbrother.com.doctorguide.view.AddCommentView;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModelImpl;
import kosbrother.com.doctorguide.viewmodel.DatePickerViewModel;


public class AddCommentActivity extends BaseActivity implements
        DatePickerDialog.OnDateSetListener,
        AddDivisionCommentFragment.EnablePagerSlide,
        PassParamsToActivity,
        AddCommentView,
        AdapterView.OnItemSelectedListener {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private ProgressDialog mProgressDialog;

    private AddCommentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddCommentViewModel viewModel = new AddCommentViewModelImpl(getIntent());
        presenter = new AddCommentPresenter(this, new AddCommentModelImpl(viewModel));
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_add_comment);
    }

    @Override
    public void initToolbar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle(getString(R.string.add_comment_title));
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void initTabLayoutWithViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddDivisionCommentFragment(), getString(R.string.add_comment_division_title));
        adapter.addFragment(new AddDoctorCommentFragment(), getString(R.string.add_comment_doctor_title));

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setHospitalNameText(String hospitalName) {
        ((TextView) findViewById(R.id.hospial_name)).setText(hospitalName);
    }

    @Override
    public void setDateText(String date) {
        ((TextView) findViewById(R.id.date)).setText(date);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void setDivisionSpinner(ArrayList<String> divs, int position) {
        Spinner div_spinner = (Spinner) findViewById(R.id.div_selector);
        setSpinner(divs, position, div_spinner);
    }

    @Override
    public void setDoctorSpinner(ArrayList<String> drs, int position) {
        Spinner dr_spinner = (Spinner) findViewById(R.id.dr_selector);
        setSpinner(drs, position, dr_spinner);
    }

    private void setSpinner(ArrayList<String> data, int position, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(position, false);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void sendAddCommentClickDivisionSpinnerEvent(String division) {
        GAManager.sendEvent(new AddCommentClickDivisionSpinnerEvent(division));
    }

    @Override
    public void sendAddCommentClickDoctorSpinnerEvent(String doctorFromPosition) {
        GAManager.sendEvent(new AddCommentClickDoctorSpinnerEvent(doctorFromPosition));
    }

    @Override
    public void sendAddCommentClickDateTextEvent(String dateSetString) {
        GAManager.sendEvent(new AddCommentClickDateTextEvent(dateSetString));
    }

    @Override
    public void sendIsDirectSubmitToBroadcast(boolean isDirectSubmit) {
        sendBroadcast(new Intent("fragmentupdater").putExtra("directSubmit", isDirectSubmit));
    }

    @Override
    public void showDatePickerDialog(DatePickerViewModel datePickerViewModel) {
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                datePickerViewModel.getYear(),
                datePickerViewModel.getMonth(),
                datePickerViewModel.getDay());
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void showPostCommentResultSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("評論發表成功")
                .setMessage("謝謝你發表評論，讓資料更完善！")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void superOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void disableTabClickAndPagerSwipe() {
        setPagerSwipeAndTabClickEnabled(false);
    }

    @Override
    public void enableTabClickAndPagerSwipe() {
        setPagerSwipeAndTabClickEnabled(true);
    }

    private void setPagerSwipeAndTabClickEnabled(boolean enabled) {
        viewPager.setPagingEnabled(enabled);

        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.setEnabled(enabled);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(enabled);
        }
    }

    @Override
    public void moveToDivisionPage() {
        viewPager.setCurrentItem(0);
    }

    @Override
    public void moveToDoctorPage() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.div_selector:
                presenter.onDivisionItemSelected(position);
                break;
            case R.id.dr_selector:
                presenter.onDoctorItemSelected(position);
                break;
            default:
                break;
        }
    }

    public void onDateButtonClick(View v) {
        presenter.onDateButtonClick();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int y, int m, int dayOfMonth) {
        presenter.onDateSet(y, m, dayOfMonth);
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed(viewPager.getCurrentItem() == 1);
    }

    @Override
    public void passParams(HashMap<String, String> map) {
        presenter.onPassParams(map);
    }

    @Override
    public void onSubmitClick() {
        presenter.onSubmitClick();
    }

    public void onDivisionNextClick() {
        presenter.onDivisionNextClick();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
