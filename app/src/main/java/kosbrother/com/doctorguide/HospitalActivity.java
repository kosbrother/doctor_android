package kosbrother.com.doctorguide;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionListFragment;
import kosbrother.com.doctorguide.fragments.HospitalDetailFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickAddCommentEvent;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickCollectEvent;
import kosbrother.com.doctorguide.model.ClickAddCommentModel;
import kosbrother.com.doctorguide.model.ClickProblemReportModel;
import kosbrother.com.doctorguide.model.HospitalModel;
import kosbrother.com.doctorguide.presenter.ClickAddCommentPresenter;
import kosbrother.com.doctorguide.presenter.ClickProblemReportPresenter;
import kosbrother.com.doctorguide.presenter.ClickSharePresenter;
import kosbrother.com.doctorguide.presenter.HospitalPresenter;
import kosbrother.com.doctorguide.view.ClickAddCommentView;
import kosbrother.com.doctorguide.view.ClickAddDoctorView;
import kosbrother.com.doctorguide.view.ClickProblemReportView;
import kosbrother.com.doctorguide.view.ClickShareView;
import kosbrother.com.doctorguide.view.HospitalView;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.AddDoctorViewModel;
import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.HospitalScoreViewModel;
import kosbrother.com.doctorguide.viewmodel.ProblemReportViewModel;

public class HospitalActivity extends GoogleSignInActivity implements
        DivisionListFragment.OnListFragmentInteractionListener,
        HospitalView,
        ClickAddCommentView,
        ClickAddDoctorView,
        ClickProblemReportView,
        ClickShareView {

    private GoogleApiClient mClient;

    private HospitalPresenter hospitalPresenter;
    private ClickAddCommentPresenter clickAddCommentPresenter;
    private ClickProblemReportPresenter clickProblemReportPresenter;
    private ClickSharePresenter clickSharePresenter;

    private ProgressDialog progressDialog;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HospitalActivityViewModel viewModel = new HospitalActivityViewModel(getIntent());

        Realm realm = Realm.getInstance(getBaseContext());
        hospitalPresenter = new HospitalPresenter(this, new HospitalModel(viewModel, realm));
        hospitalPresenter.onCreate();

        clickAddCommentPresenter = new ClickAddCommentPresenter(this, new ClickAddCommentModel(viewModel));
        clickProblemReportPresenter = new ClickProblemReportPresenter(this, new ClickProblemReportModel(viewModel));
        clickSharePresenter = new ClickSharePresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        hospitalPresenter.onStart();
    }

    @Override
    protected void onStop() {
        hospitalPresenter.onStop();
        super.onStop();
    }

    @Override
    public void buildAppIndexClient() {
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void connectAppIndexClient() {
        mClient.connect();
    }

    @Override
    public void disConnectAppIndexClient() {
        mClient.disconnect();
    }

    @Override
    public void startAppIndexApi(String hospitalName, Uri webUrl, Uri appUri) {
        // Construct the Action performed by the user
        Action viewAction = Action.newAction(Action.TYPE_VIEW, hospitalName,
                webUrl, appUri);
        // Call the App Indexing API start method after the view has completely rendered
        AppIndex.AppIndexApi.start(mClient, viewAction);
    }

    @Override
    public void endAppIndexApi(String hospitalName, Uri webUrl, Uri appUri) {
        Action viewAction = Action.newAction(Action.TYPE_VIEW, hospitalName,
                webUrl, appUri);
        AppIndex.AppIndexApi.end(mClient, viewAction);
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
                hospitalPresenter.onHeartButtonClick();
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

    public void sendHospitalClickAddCommentEvent(String label) {
        GAManager.sendEvent(new HospitalClickAddCommentEvent(label));
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
            hospitalPresenter.onDivisionInfoClick(division);
        } else {
            hospitalPresenter.onDivisionClick(division);
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

    @Override
    public void startShareActivity() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void onAddCommentClick() {
        hospitalPresenter.onAddCommentClick();
        clickAddCommentPresenter.startAddComment();
    }

    @Override
    public void showSignInDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);

        SignInButton signInBtn = (SignInButton) dialog.findViewById(R.id.sign_in_button);
        signInBtn.setSize(SignInButton.SIZE_WIDE);
        signInBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAddCommentPresenter.onSignInButtonClick();
            }
        });
        dialog.show();
    }

    @Override
    public void dismissSignInDialog() {
        dialog.dismiss();
    }

    @Override
    public void showCreateUserFailToast() {
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startAddCommentActivity(AddCommentViewModel addCommentViewModel) {
        Intent intent = new Intent(this, AddCommentActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, addCommentViewModel.getHospitalId());
        intent.putExtra(ExtraKey.DIVISION_ID, addCommentViewModel.getDivisionId());
        intent.putExtra(ExtraKey.DOCTOR_ID, addCommentViewModel.getDoctorId());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, addCommentViewModel.getHospitalName());
        intent.putExtra(ExtraKey.USER, addCommentViewModel.getUser());
        startActivity(intent);
    }

    @Override
    public void startAddDoctorActivity(AddDoctorViewModel addDoctorViewModel) {
        Intent intent = new Intent(this, AddDoctorActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, addDoctorViewModel.getHospitalName());
        intent.putExtra(ExtraKey.DIVISION_NAME, addDoctorViewModel.getDivisionName());
        intent.putExtra(ExtraKey.HOSPITAL_ID, addDoctorViewModel.getHospitalId());
        intent.putExtra(ExtraKey.DIVISION_ID, addDoctorViewModel.getDivisionId());
        startActivity(intent);
    }

    @Override
    public void startProblemReportActivity(ProblemReportViewModel viewModel) {
        Intent intent = new Intent(this, ProblemReportActivity.class);
        intent.putExtra(ExtraKey.REPORT_TYPE, getString(R.string.hospital_page));
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_and_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_item:
                clickSharePresenter.onShareClick();
                return true;
            case R.id.report_problem_item:
                clickProblemReportPresenter.onProblemReportClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
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

