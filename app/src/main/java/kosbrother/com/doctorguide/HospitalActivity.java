package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionListFragment;
import kosbrother.com.doctorguide.fragments.HospitalDetailFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickCollectEvent;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickFABEvent;
import kosbrother.com.doctorguide.model.HospitalFabModel;
import kosbrother.com.doctorguide.model.HospitalModel;
import kosbrother.com.doctorguide.presenter.HospitalFabPresenter;
import kosbrother.com.doctorguide.presenter.HospitalPresenter;
import kosbrother.com.doctorguide.view.HospitalFabView;
import kosbrother.com.doctorguide.view.HospitalView;
import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.HospitalScoreViewModel;

public class HospitalActivity extends BaseActivity implements
        DivisionListFragment.OnListFragmentInteractionListener,
        HospitalView, HospitalFabView {

    private HospitalPresenter presenter;
    private HospitalFabPresenter fabPresenter;

    private FloatingActionMenu fab;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HospitalActivityViewModel viewModel = new HospitalActivityViewModel(getIntent());
        Realm realm = Realm.getInstance(getBaseContext());
        presenter = new HospitalPresenter(this, new HospitalModel(viewModel, realm));
        presenter.onCreate();

        fabPresenter = new HospitalFabPresenter(this, new HospitalFabModel(viewModel));
        fabPresenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_hospital);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("醫院資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void setHospitalImage(int hospitalImageRedId) {
        ((ImageView) findViewById(R.id.div_image)).setImageResource(hospitalImageRedId);
    }

    @Override
    public void setHospitalName(String hospitalName) {
        ((TextView) findViewById(R.id.hospial_name)).setText(hospitalName);
    }

    @Override
    public void setHeartButtonBackground(int resId) {
        findViewById(R.id.heart).setBackgroundResource(resId);
    }

    @Override
    public void setHeartButton() {
        findViewById(R.id.heart).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onHeartButtonClick();
            }
        });
    }

    @Override
    public void setHospitalScore(HospitalScoreViewModel scoreViewModel) {
        ((TextView) findViewById(R.id.comment_num)).setText(scoreViewModel.getCommentNum());
        ((TextView) findViewById(R.id.recommend_num)).setText(scoreViewModel.getRecommendNum());
        ((TextView) findViewById(R.id.score)).setText(scoreViewModel.getAvgScore());
    }

    @Override
    public void setViewPager(int hospitalId, Hospital hospital) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HospitalDetailFragment.newInstance(hospital), "關於本院");
        adapter.addFragment(DivisionListFragment.newInstance(hospital.divisions), "院內科別");
        adapter.addFragment(CommentFragment.newInstance(hospitalId, null, null, GACategory.HOSPITAL), "本院評論");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void sendHospitalClickCollectEvent(String hospitalName) {
        GAManager.sendEvent(new HospitalClickCollectEvent(hospitalName));
    }

    @Override
    public void showRemoveFromCollectSuccessSnackBar() {
        showSnackBar("取消收藏", Color.RED);
    }

    @Override
    public void showAddToCollectSuccessSnackBar() {
        showSnackBar("成功收藏", ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
    }

    @Override
    public void showDivisionInfoDialog(Division division) {
        Category categoryById = Category.getCategoryById(division.category_id);
        if (categoryById != null) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(HospitalActivity.this, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(categoryById.name);
            builder.setMessage(categoryById.intro);
            builder.setPositiveButton("確定", null);
            builder.show();
        }
    }

    @Override
    public void startDivisionActivity(Division division) {
        Intent intent = new Intent(HospitalActivity.this, DivisionActivity.class);
        intent.putExtra(ExtraKey.DIVISION_ID, division.id);
        intent.putExtra(ExtraKey.DIVISION_NAME, division.name);
        intent.putExtra(ExtraKey.HOSPITAL_ID, division.hospital_id);
        intent.putExtra(ExtraKey.HOSPITAL_GRADE, division.hospital_grade);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, division.hospital_name);
        startActivity(intent);
    }

    private void showSnackBar(String message, int color) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.heart), message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onListFragmentInteraction(View view, Division division) {
        if (view.getId() == R.id.detail_button) {
            presenter.onDivisionInfoClick(division);
        } else {
            presenter.onDivisionClick(division);
        }
    }

    @Override
    public void showProgressDialog() {
        progressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_problem_report:
                    fabPresenter.onProblemReportClick();
                    break;
                case R.id.fab_share:
                    fabPresenter.onFabShareClick();
                    break;
                case R.id.fab_add_doctor:
                    fabPresenter.onAddDoctorClick();
                    break;
            }
        }
    };

    @Override
    public void initFab() {
        fab = (FloatingActionMenu) findViewById(R.id.menu2);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                fabPresenter.onFabMenuToggle(opened);
            }
        });
        fab.setClosedOnTouchOutside(true);

        findViewById(R.id.fab_problem_report).setOnClickListener(clickListener);
        findViewById(R.id.fab_share).setOnClickListener(clickListener);
        findViewById(R.id.fab_add_doctor).setOnClickListener(clickListener);
    }

    @Override
    public void setFabImageDrawable(int fabDrawableId) {
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), fabDrawableId);
        fab.getMenuIconView().setImageDrawable(drawable);
    }

    @Override
    public void closeFab() {
        fab.close(true);
    }

    @Override
    public void sendClickFabEvent(String label) {
        GAManager.sendEvent(new HospitalClickFABEvent(label));
    }

    @Override
    public void startShareActivity() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void startProblemReportActivity(HospitalActivityViewModel viewModel) {
        Intent intent = new Intent(HospitalActivity.this, ProblemReportActivity.class);
        intent.putExtra(ExtraKey.REPORT_TYPE, getString(R.string.hospital_page));
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        startActivity(intent);
    }

    @Override
    public void startAddDoctorActivity(HospitalActivityViewModel viewModel) {
        Intent intent = new Intent(HospitalActivity.this, AddDoctorActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
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

}
