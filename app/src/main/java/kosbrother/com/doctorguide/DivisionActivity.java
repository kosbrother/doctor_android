package kosbrother.com.doctorguide;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionScoreFragment;
import kosbrother.com.doctorguide.fragments.DoctorFragment;

public class DivisionActivity extends AppCompatActivity implements DoctorFragment.OnListFragmentInteractionListener, GoogleApiClient.OnConnectionFailedListener {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabProblemReport;
    private FloatingActionButton fabShare;
    private FloatingActionButton fabComment;
    private FloatingActionButton fabAddDoctor;
    private GoogleApiClient mGoogleApiClient;
    private boolean isSignIn;
    private static final int RC_SIGN_IN = 9002;
    private FloatingActionMenu fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        actionbar = getSupportActionBar();
        actionbar.setTitle("科別資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        setSpinner();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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

        fabProblemReport = (FloatingActionButton) findViewById(R.id.fab_problem_report);
        fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        fabComment = (FloatingActionButton) findViewById(R.id.fab_comment);
        fabAddDoctor = (FloatingActionButton) findViewById(R.id.fab_add_doctor);

        fabProblemReport.setOnClickListener(clickListener);
        fabShare.setOnClickListener(clickListener);
        fabComment.setOnClickListener(clickListener);
        fabAddDoctor.setOnClickListener(clickListener);

        googleSignIn();
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            isSignIn = true;
        }else{
            isSignIn = false;
        }
    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.HEARTTYPE,1), "科內醫生");
        adapter.addFragment(new DivisionScoreFragment(), "本科評分");
        adapter.addFragment(new CommentFragment(), "本科評論");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onListFragmentInteraction(Doctor item) {
        Intent intent = new Intent(this, DoctorActivity.class);
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

    private void setSpinner() {
        Spinner spinner = (Spinner)findViewById(R.id.division_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.divisions, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void onHospitalClick(View v) {
        Intent intent = new Intent(this, HospitalActivity.class);
        startActivity(intent);
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

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fab.close(true);
            Intent intent;
            switch (v.getId()) {
                case R.id.fab_problem_report:
                    intent = new Intent(DivisionActivity.this, ProblemReportActivity.class);
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
                        final Dialog dialog = new Dialog(DivisionActivity.this);
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
                case R.id.fab_add_doctor:
                    intent = new Intent(DivisionActivity.this, AddDoctorActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private void startCommentActivity() {
        Intent intent = new Intent(DivisionActivity.this, AddCommentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            startCommentActivity();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
