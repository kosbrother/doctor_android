package kosbrother.com.doctorguide;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DoctorDetailFragment;
import kosbrother.com.doctorguide.fragments.DoctorScoreFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickCollectEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickFABEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;

public class DoctorActivity extends GoogleSignInActivity implements DoctorScoreFragment.GetDoctor,
        CreateUserTask.CreateUserListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int doctorId;
    private String doctorName;
    private boolean collected;
    private FloatingActionMenu fab;
    private String hospitalName;
    private Doctor doctor;
    private int hospitalId;
    private String email;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorId = extras.getInt(ExtraKey.DOCTOR_ID);
            doctorName = extras.getString(ExtraKey.DOCTOR_NAME);
            hospitalName = extras.getString(ExtraKey.HOSPITAL_NAME);
            hospitalId = extras.getInt(ExtraKey.HOSPITAL_ID);
        }

        actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("醫師資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setHeatButton();
        setFab();
        new GetDoctorScoreTask().execute();
    }

    @Override
    public Doctor getDodctor() {
        return doctor;
    }

    private class GetDoctorScoreTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(DoctorActivity.this);
        }

        @Override
        protected Object doInBackground(Object... params) {
            doctor = DoctorGuideApi.getDoctorScore(doctorId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            TextView mCommentNum = (TextView) findViewById(R.id.comment_num);
            TextView mRecommendNum = (TextView) findViewById(R.id.recommend_num);
            TextView mScore = (TextView) findViewById(R.id.score);
            TextView dName = (TextView) findViewById(R.id.doctor_name);

            mCommentNum.setText(doctor.comment_num + "");
            mRecommendNum.setText(doctor.recommend_num + "");
            mScore.setText(String.format("%.1f", doctor.avg));
            dName.setText(doctorName + " 醫師");

            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    private void setFab() {
        fab = (FloatingActionMenu) findViewById(R.id.menu2);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                GAManager.sendEvent(new DoctorClickFABEvent(GALabel.FAB_MENU));

                int drawableId;
                if (opened) {
                    drawableId = R.mipmap.ic_close;
                } else {
                    drawableId = R.mipmap.ic_fab;
                }
                Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), drawableId);
                fab.getMenuIconView().setImageDrawable(drawable);
            }
        });
        fab.setClosedOnTouchOutside(true);

        FloatingActionButton fabProblemReport = (FloatingActionButton) findViewById(R.id.fab_problem_report);
        FloatingActionButton fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        FloatingActionButton fabComment = (FloatingActionButton) findViewById(R.id.fab_comment);

        fabProblemReport.setOnClickListener(clickListener);
        fabShare.setOnClickListener(clickListener);
        fabComment.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fab.close(true);
            Intent intent;
            switch (v.getId()) {
                case R.id.fab_problem_report:
                    GAManager.sendEvent(new DoctorClickFABEvent(GALabel.PROBLEM_REPORT));

                    intent = new Intent(DoctorActivity.this, ProblemReportActivity.class);
                    intent.putExtra(ExtraKey.REPORT_TYPE, getString(R.string.doctor_page));
                    intent.putExtra(ExtraKey.HOSPITAL_NAME, hospitalName);
                    intent.putExtra(ExtraKey.DOCTOR_NAME, doctorName);
                    intent.putExtra(ExtraKey.DOCTOR_ID, doctorId);
                    intent.putExtra(ExtraKey.HOSPITAL_ID, hospitalId);
                    startActivity(intent);
                    break;
                case R.id.fab_share:
                    GAManager.sendEvent(new DoctorClickFABEvent(GALabel.SHARE));

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case R.id.fab_comment:
                    GAManager.sendEvent(new DoctorClickFABEvent(GALabel.COMMENT));

                    if (isSignIn) {
                        startCommentActivity();
                    } else {
                        final Dialog dialog = new Dialog(DoctorActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_login);

                        SignInButton signInBtn = (SignInButton) dialog.findViewById(R.id.sign_in_button);
                        signInBtn.setSize(SignInButton.SIZE_WIDE);
                        signInBtn.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                signIn();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void handleSignInResult(GoogleSignInResult result) {
        super.handleSignInResult(result);
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            email = acct.getEmail();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && isSignIn) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            User user = new User();
            user.email = acct.getEmail();
            user.name = acct.getDisplayName();
            if (acct.getPhotoUrl() != null)
                user.pic_url = acct.getPhotoUrl().toString();
            mProgressDialog = Util.showProgressDialog(this);
            new CreateUserTask(this).execute(user);
        }
    }

    @Override
    public void onCreateUserSuccess() {
        mProgressDialog.dismiss();
        startCommentActivity();
    }

    @Override
    public void onCreateUserFail() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
    }

    private void startCommentActivity() {
        Intent intent = new Intent(DoctorActivity.this, AddCommentActivity.class);
        intent.putExtra(ExtraKey.DOCTOR_ID, doctorId);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, hospitalName);
        intent.putExtra(ExtraKey.HOSPITAL_ID, hospitalId);
        intent.putExtra(ExtraKey.USER, email);
        startActivity(intent);
    }

    private void setHeatButton() {
        final Button heart = (Button) findViewById(R.id.heart);
        final Realm realm = Realm.getInstance(getBaseContext());
        RealmDoctor doctor = realm.where(RealmDoctor.class).equalTo("id", doctorId).findFirst();
        if (doctor == null) {
            collected = false;
            heart.setBackgroundResource(R.drawable.heart_white_to_red_button);
        } else {
            collected = true;
            heart.setBackgroundResource(R.drawable.heart_read_to_white_button);
        }

        heart.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAManager.sendEvent(new DoctorClickCollectEvent(doctorName));

                if (collected) {

                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmDoctor doc = realm.where(RealmDoctor.class).equalTo("id", doctorId).findFirst();
                            doc.removeFromRealm();
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
                            RealmDoctor doctor = realm.createObject(RealmDoctor.class);
                            doctor.setId(doctorId);
                            doctor.setName(doctorName);
                            doctor.setHospital(hospitalName);
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
        adapter.addFragment(DoctorDetailFragment.newInstance(doctorId), "醫師資料");
        adapter.addFragment(DoctorScoreFragment.newInstance(), "醫師評分");
        adapter.addFragment(CommentFragment.newInstance(null, null, doctorId, GACategory.DOCTOR), "醫師評論");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
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
