package kosbrother.com.doctorguide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.CreateUserTask;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionScoreFragment;
import kosbrother.com.doctorguide.fragments.DoctorFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickDivisionSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickFABEvent;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickHospitalTextEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;

public class DivisionActivity extends GoogleSignInActivity implements DoctorFragment.OnListFragmentInteractionListener, DivisionScoreFragment.GetDivision, CreateUserTask.AfterCreateUser {

    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fabProblemReport;
    private FloatingActionButton fabShare;
    private FloatingActionButton fabComment;
    private FloatingActionButton fabAddDoctor;
    private FloatingActionMenu fab;
    private int hospitalId;
    private ArrayList<Division> hospitalDivisions;
    private int divisionId;
    private String divisionName;
    private boolean startInteract = false;
    private String hospitalGrade;
    private String hospitalName;
    private ViewPagerAdapter adapter;
    private Division division;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);

        actionbar = getSupportActionBar();
        actionbar.setTitle("科別資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            divisionId = extras.getInt("DIVISION_ID");
            divisionName = extras.getString("DIVISION_NAME");
            hospitalId = extras.getInt("HOSPITAL_ID");
            hospitalGrade = extras.getString("HOSPITAL_GRADE");
            hospitalName = extras.getString("HOSPITAL_NAME");
        }

        setViews();
        setSpinner();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setFab();
        new GetDivisionScoreTask().execute();
    }

    @Override
    public Division getDivision() {
        return division;
    }

    private class GetDivisionScoreTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(DivisionActivity.this);
        }

        @Override
        protected Object doInBackground(Object... params) {
            division = DoctorGuideApi.getDivisionScore(divisionId, hospitalId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            TextView mCommentNum = (TextView) findViewById(R.id.comment_num);
            TextView mRecommendNum = (TextView) findViewById(R.id.recommend_num);
            TextView mScore = (TextView) findViewById(R.id.score);

            mCommentNum.setText(division.comment_num + "");
            mRecommendNum.setText(division.recommend_num + "");
            mScore.setText(String.format("%.1f", division.avg));

            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    private void setFab() {
        fab = (FloatingActionMenu) findViewById(R.id.menu2);
        fab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                GAManager.sendEvent(new DivisionClickFABEvent(GALabel.FAB_MENU));

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

        fabProblemReport = (FloatingActionButton) findViewById(R.id.fab_problem_report);
        fabShare = (FloatingActionButton) findViewById(R.id.fab_share);
        fabComment = (FloatingActionButton) findViewById(R.id.fab_comment);
        fabAddDoctor = (FloatingActionButton) findViewById(R.id.fab_add_doctor);

        fabProblemReport.setOnClickListener(clickListener);
        fabShare.setOnClickListener(clickListener);
        fabComment.setOnClickListener(clickListener);
        fabAddDoctor.setOnClickListener(clickListener);
    }

    private void setViews() {
        ImageView divImage = (ImageView) findViewById(R.id.div_image);
        TextView hospital = (TextView) findViewById(R.id.hospial_name);
        switch (hospitalGrade) {
            case "醫學中心":
                divImage.setImageResource(R.mipmap.ic_hospital_biggest);
                break;
            case "區域醫院":
                divImage.setImageResource(R.mipmap.ic_hospital_medium);
                break;
            case "地區醫院":
                divImage.setImageResource(R.mipmap.ic_hospital_small);
                break;
            case "診所":
                divImage.setImageResource(R.mipmap.ic_hospital_smallest);
                break;
        }
        String htmlString = "<u>" + hospitalName + "</u>";
        hospital.setText(Html.fromHtml(htmlString));
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.HEARTTYPE, hospitalId, divisionId), "科內醫生");
        adapter.addFragment(DivisionScoreFragment.newInstance(), "本科評分");
        adapter.addFragment(CommentFragment.newInstance(hospitalId, divisionId, null, GACategory.DIVISION), "本科評論");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(View view, final Doctor item) {
        if (view.getId() == R.id.heart) {
            if (item.isCollected) {
                String message = "確定要取消收藏「" + item.name + " 醫師" + "」嗎？";
                new AlertDialog.Builder(DivisionActivity.this, R.style.AppCompatAlertDialogStyle)
                        .setTitle("取消收藏")
                        .setMessage(message)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Realm realm = Realm.getInstance(getBaseContext());
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        RealmDoctor doc = realm.where(RealmDoctor.class).equalTo("id", item.id).findFirst();
                                        doc.removeFromRealm();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            } else {
                Realm realm = Realm.getInstance(getBaseContext());
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmDoctor doctor = new RealmDoctor();
                        doctor.setId(item.id);
                        doctor.setName(item.name);
                        doctor.setAddress(item.address);
                        doctor.setHospital(hospitalName);
                        doctor.setHospitalId(hospitalId);
                        realm.copyToRealmOrUpdate(doctor);
                    }
                });
                Snackbar snackbar = Snackbar.make(tabLayout, "成功收藏", Snackbar.LENGTH_SHORT);
                View snackbrView = snackbar.getView();
                TextView tv = (TextView) snackbrView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                snackbar.show();
            }
            adapter.notifyDataSetChanged();
        } else {
            Intent intent = new Intent(this, DoctorActivity.class);
            intent.putExtra("HOSPITAL_ID", hospitalId);
            intent.putExtra("DOCTOR_ID", item.id);
            intent.putExtra("DOCTOR_NAME", item.name);
            intent.putExtra("HOSPITAL_NAME", hospitalName);
            startActivity(intent);
        }
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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private void setSpinner() {
        new SetDivisionTask().execute();
    }

    private class SetDivisionTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(DivisionActivity.this);
        }

        @Override
        protected Object doInBackground(Object... params) {
            hospitalDivisions = DoctorGuideApi.getDivisionByHospital(hospitalId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();

            Spinner spinner = (Spinner) findViewById(R.id.division_spinner);
            List<String> strings = new ArrayList<>();
            for (Division div : hospitalDivisions)
                strings.add(div.name);

            ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(DivisionActivity.this,
                    R.layout.spinner_item, strings.toArray(new String[strings.size()]));
            areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(areaAdapter);
            int spinnerPosition = areaAdapter.getPosition(divisionName);
            spinner.setSelection(spinnerPosition);
            spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    GAManager.sendEvent(new DivisionClickDivisionSpinnerEvent(hospitalDivisions.get(position).name));

                    if (startInteract) {
                        Intent intent = new Intent(DivisionActivity.this, DivisionActivity.class);
                        intent.putExtra("DIVISION_ID", hospitalDivisions.get(position).id);
                        intent.putExtra("DIVISION_NAME", hospitalDivisions.get(position).name);
                        intent.putExtra("HOSPITAL_ID", hospitalId);
                        intent.putExtra("HOSPITAL_GRADE", hospitalGrade);
                        intent.putExtra("HOSPITAL_NAME", hospitalName);
                        startActivity(intent);
                        finish();
                    } else
                        startInteract = true;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    public void onHospitalClick(View v) {
        GAManager.sendEvent(new DivisionClickHospitalTextEvent(hospitalName));

        Intent intent = new Intent(this, HospitalActivity.class);
        intent.putExtra("HOSPITAL_ID", hospitalId);
        intent.putExtra("HOSPITAL_GRADE", hospitalGrade);
        intent.putExtra("HOSPITAL_NAME", hospitalName);
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
                    GAManager.sendEvent(new DivisionClickFABEvent(GALabel.PROBLEM_REPORT));

                    intent = new Intent(DivisionActivity.this, ProblemReportActivity.class);
                    intent.putExtra("REPORT_TYPE", getString(R.string.division_page));
                    intent.putExtra("HOSPITAL_NAME", hospitalName);
                    intent.putExtra("DIVISION_NAME", divisionName);
                    intent.putExtra("DIVISION_ID", divisionId);
                    intent.putExtra("HOSPITAL_ID", hospitalId);
                    startActivity(intent);
                    break;
                case R.id.fab_share:
                    GAManager.sendEvent(new DivisionClickFABEvent(GALabel.SHARE));

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    break;
                case R.id.fab_comment:
                    GAManager.sendEvent(new DivisionClickFABEvent(GALabel.COMMENT));

                    if (isSignIn) {
                        startCommentActivity();
                    } else {
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
                    GAManager.sendEvent(new DivisionClickFABEvent(GALabel.ADD_DOCTOR));

                    intent = new Intent(DivisionActivity.this, AddDoctorActivity.class);
                    intent.putExtra("HOSPITAL_NAME", hospitalName);
                    intent.putExtra("DIVISION_NAME", divisionName);
                    intent.putExtra("HOSPITAL_ID", hospitalId);
                    intent.putExtra("DIVISION_ID", divisionId);
                    startActivity(intent);
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
            new CreateUserTask(this, user).execute();
        }
    }

    @Override
    public void afterCreateUser() {
        startCommentActivity();
    }

    private void startCommentActivity() {
        Intent intent = new Intent(DivisionActivity.this, AddCommentActivity.class);
        intent.putExtra("HOSPITAL_ID", hospitalId);
        intent.putExtra("DIVISION_ID", divisionId);
        intent.putExtra("HOSPITAL_NAME", hospitalName);
        intent.putExtra("USER", email);
        startActivity(intent);
    }

}
