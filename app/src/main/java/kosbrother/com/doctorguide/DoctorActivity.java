package kosbrother.com.doctorguide;

import android.app.Dialog;
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
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
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
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickCollectEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickFABEvent;
import kosbrother.com.doctorguide.model.DoctorFabModel;
import kosbrother.com.doctorguide.model.DoctorModel;
import kosbrother.com.doctorguide.presenter.DoctorFabPresenter;
import kosbrother.com.doctorguide.presenter.DoctorPresenter;
import kosbrother.com.doctorguide.view.DoctorFabView;
import kosbrother.com.doctorguide.view.DoctorView;
import kosbrother.com.doctorguide.viewmodel.DoctorActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorScoreViewModel;

public class DoctorActivity extends GoogleSignInActivity implements
        DoctorScoreFragment.GetDoctor,
        DoctorView,
        DoctorFabView {

    private DoctorPresenter doctorPresenter;
    private DoctorFabPresenter fabPresenter;

    private FloatingActionMenu fab;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DoctorActivityViewModel viewModel = new DoctorActivityViewModel(getIntent());
        Realm realm = Realm.getInstance(getBaseContext());
        doctorPresenter = new DoctorPresenter(this, new DoctorModel(viewModel, realm));
        doctorPresenter.onCreate();

        fabPresenter = new DoctorFabPresenter(this, new DoctorFabModel(viewModel));
        fabPresenter.onCreate();
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fab_problem_report:
                    fabPresenter.onFabProblemReportClick();
                    break;
                case R.id.fab_share:
                    fabPresenter.onFabShareClick();
                    break;
                case R.id.fab_comment:
                    fabPresenter.onFabCommentClick();
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                fabPresenter.onSignInActivityResultSuccess();
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
                fabPresenter.onSignInButtonClick();
            }
        });
        dialog.show();
    }

    @Override
    public void showCreateUserFailToast() {
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissSignInDialog() {
        dialog.dismiss();
    }

    @Override
    public void startCommentActivity(DoctorActivityViewModel viewModel, String email) {
        Intent intent = new Intent(DoctorActivity.this, AddCommentActivity.class);
        intent.putExtra(ExtraKey.DOCTOR_ID, viewModel.getDoctorId());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        intent.putExtra(ExtraKey.USER, email);
        startActivity(intent);
    }

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
        findViewById(R.id.fab_comment).setOnClickListener(clickListener);
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
        GAManager.sendEvent(new DoctorClickFABEvent(label));
    }

    @Override
    public void startProblemReportActivity(DoctorActivityViewModel viewModel) {
        Intent intent = new Intent(DoctorActivity.this, ProblemReportActivity.class);
        intent.putExtra(ExtraKey.REPORT_TYPE, getString(R.string.doctor_page));
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.DOCTOR_NAME, viewModel.getDoctorName());
        intent.putExtra(ExtraKey.DOCTOR_ID, viewModel.getDoctorId());
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        startActivity(intent);
    }

    @Override
    public void startShareActivity() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
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
