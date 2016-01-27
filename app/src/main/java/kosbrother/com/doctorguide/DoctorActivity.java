package kosbrother.com.doctorguide;

import com.google.android.gms.common.SignInButton;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DoctorDetailFragment;
import kosbrother.com.doctorguide.fragments.DoctorScoreFragment;

public class DoctorActivity extends GoogleSignInActivity {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int doctorId;
    private String doctorName;
    private boolean collected;
    private FloatingActionMenu fab;
    private String hospitalName;
    private Doctor doctor;
//    private int commentNum;
//    private int recommendNum;
//    private float avg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            doctorId = extras.getInt("DOCTOR_ID");
            doctorName = extras.getString("DOCTOR_NAME");
            hospitalName = extras.getString("HOSPITAL_NAME");
        }

        actionbar = getSupportActionBar();
        actionbar.setTitle("醫師資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setHeatButton();
        setFab();
        new GetDoctorScoreTask().execute();
    }

    private class GetDoctorScoreTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Util.showProgressDialog(DoctorActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            doctor = DoctorGuideApi.getDoctorScore(doctorId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            Util.hideProgressDialog();
            TextView mCommentNum = (TextView) findViewById(R.id.comment_num);
            TextView mRecommendNum = (TextView) findViewById(R.id.recommend_num);
            TextView mScore = (TextView) findViewById(R.id.score);
            TextView dName = (TextView)findViewById(R.id.doctor_name);

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
                    intent = new Intent(DoctorActivity.this, ProblemReportActivity.class);
                    intent.putExtra("REPORT_TYPE",getString(R.string.doctor_page));
                    intent.putExtra("HOSPITAL_NAME",hospitalName);
                    intent.putExtra("DOCTOR_NAME",doctorName);
                    startActivity(intent);
                    break;
                case R.id.fab_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case R.id.fab_comment:
                    if(isSignIn){
                        startCommentActivity();
                    }else {
                        final Dialog dialog = new Dialog(DoctorActivity.this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && isSignIn) {
            startCommentActivity();
        }
    }

    private void startCommentActivity() {
        Intent intent = new Intent(DoctorActivity.this, AddCommentActivity.class);
        startActivity(intent);
    }

    private void setHeatButton() {
        final Button heart = (Button)findViewById(R.id.heart);
        final Realm realm = Realm.getInstance(getBaseContext());
        RealmDoctor doctor = realm.where(RealmDoctor.class).equalTo("id", doctorId).findFirst();
        if(doctor == null) {
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
        adapter.addFragment(DoctorScoreFragment.newInstance(doctor), "醫師評分");
        adapter.addFragment(CommentFragment.newInstance(null,null,doctorId), "醫師評論");
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
