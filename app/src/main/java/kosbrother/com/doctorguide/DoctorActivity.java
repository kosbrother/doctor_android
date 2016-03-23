package kosbrother.com.doctorguide;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DoctorDetailFragment;
import kosbrother.com.doctorguide.fragments.DoctorScoreFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickAddCommentEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickCollectEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickFABEvent;
import kosbrother.com.doctorguide.model.ClickAddCommentModel;
import kosbrother.com.doctorguide.model.ClickProblemReportModel;
import kosbrother.com.doctorguide.model.DoctorModel;
import kosbrother.com.doctorguide.presenter.ClickAddCommentPresenter;
import kosbrother.com.doctorguide.presenter.ClickProblemReportPresenter;
import kosbrother.com.doctorguide.presenter.ClickSharePresenter;
import kosbrother.com.doctorguide.presenter.DoctorFabPresenter;
import kosbrother.com.doctorguide.presenter.DoctorPresenter;
import kosbrother.com.doctorguide.view.ClickAddCommentView;
import kosbrother.com.doctorguide.view.ClickProblemReportView;
import kosbrother.com.doctorguide.view.ClickShareView;
import kosbrother.com.doctorguide.view.DoctorFabView;
import kosbrother.com.doctorguide.view.DoctorView;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorScoreViewModel;
import kosbrother.com.doctorguide.viewmodel.ProblemReportViewModel;

public class DoctorActivity extends GoogleSignInActivity implements
        DoctorScoreFragment.GetDoctor,
        DoctorView,
        DoctorFabView,
        ClickAddCommentView,
        ClickShareView, ClickProblemReportView {

    private DoctorPresenter doctorPresenter;
    private DoctorFabPresenter fabPresenter;

    private ClickAddCommentPresenter clickAddCommentPresenter;
    private ClickSharePresenter clickSharePresenter;
    private ClickProblemReportPresenter clickProblemReportPresenter;

    private ProgressDialog progressDialog;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoctorActivityViewModel viewModel = new DoctorActivityViewModel(getIntent());
        Realm realm = Realm.getInstance(getBaseContext());

        doctorPresenter = new DoctorPresenter(this, new DoctorModel(viewModel, realm));
        doctorPresenter.onCreate();

        clickAddCommentPresenter = new ClickAddCommentPresenter(this, new ClickAddCommentModel(viewModel));
        clickSharePresenter = new ClickSharePresenter(this);
        clickProblemReportPresenter = new ClickProblemReportPresenter(this, new ClickProblemReportModel(viewModel));

        fabPresenter = new DoctorFabPresenter(this);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_doctor);
    }

    @Override
    public void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("醫師資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void initHeartButton() {
        findViewById(R.id.heart).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                doctorPresenter.onHeartButtonClick();
            }
        });
    }

    @Override
    public void setHeartButtonBackground(int backgroundResId) {
        findViewById(R.id.heart).setBackgroundResource(backgroundResId);
    }

    @Override
    public void setDoctorName(String doctorName) {
        ((TextView) findViewById(R.id.doctor_name)).setText(doctorName + " 醫師");
    }

    @Override
    public void setDoctorScore(DoctorScoreViewModel viewModel) {
        ((TextView) findViewById(R.id.comment_num)).setText(viewModel.getCommentNum());
        ((TextView) findViewById(R.id.recommend_num)).setText(viewModel.getReCommentNum());
        ((TextView) findViewById(R.id.score)).setText(viewModel.getAvgScore());
    }

    @Override
    public void setViewPager(int doctorId) {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DoctorDetailFragment.newInstance(doctorId), "醫師資料");
        adapter.addFragment(DoctorScoreFragment.newInstance(), "醫師評分");
        adapter.addFragment(CommentFragment.newInstance(null, null, doctorId, GACategory.DOCTOR), "醫師評論");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void showCancelCollectSnackBar() {
        showSnackBar("取消收藏", Color.RED);
    }

    @Override
    public void showCollectSuccessSnackBar() {
        showSnackBar("成功收藏", ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
    }

    @Override
    public void sendDoctorClickCollectEvent(String doctorName) {
        GAManager.sendEvent(new DoctorClickCollectEvent(doctorName));
    }

    @Override
    public void sendDoctorClickAddCommentEvent(String label) {
        GAManager.sendEvent(new DoctorClickAddCommentEvent(label));
    }

    private void showSnackBar(String message, int textColor) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.heart), message, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(textColor);
        snackbar.show();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = Util.showProgressDialog(DoctorActivity.this);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public Doctor getDodctor() {
        // TODO: 2016/3/7 refactoring
        return doctorPresenter.getDoctor();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                clickAddCommentPresenter.onSignInActivityResultSuccess();
            }
        }
    }

    @Override
    public void showSignInDialog() {
        dialog = new Dialog(DoctorActivity.this);
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
    public void dismissSignInDialog() {
        dialog.dismiss();
    }

    @Override
    public void sendClickFabEvent(String label) {
        GAManager.sendEvent(new DoctorClickFABEvent(label));
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
        doctorPresenter.onAddCommentClick();
        clickAddCommentPresenter.startAddComment();
    }

    public void onFabAddCommentClick(View view) {
        fabPresenter.onFabAddCommentClick();
        clickAddCommentPresenter.startAddComment();
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

    @Override
    public void startProblemReportActivity(ProblemReportViewModel viewModel) {
        Intent intent = new Intent(this, ProblemReportActivity.class);
        intent.putExtra(ExtraKey.REPORT_TYPE, viewModel.getReportType());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.DOCTOR_NAME, viewModel.getDoctorName());
        intent.putExtra(ExtraKey.DOCTOR_ID, viewModel.getDoctorId());
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
