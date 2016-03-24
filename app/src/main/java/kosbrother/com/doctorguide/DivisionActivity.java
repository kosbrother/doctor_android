package kosbrother.com.doctorguide;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.realm.RealmDoctor;
import kosbrother.com.doctorguide.fragments.CommentFragment;
import kosbrother.com.doctorguide.fragments.DivisionScoreFragment;
import kosbrother.com.doctorguide.fragments.DoctorFragment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickAddCommentEvent;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickAddDoctorEvent;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickDivisionSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickFABEvent;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickHospitalTextEvent;
import kosbrother.com.doctorguide.model.ClickAddCommentModel;
import kosbrother.com.doctorguide.model.ClickAddDoctorModel;
import kosbrother.com.doctorguide.model.ClickProblemReportModel;
import kosbrother.com.doctorguide.model.DivisionFabModel;
import kosbrother.com.doctorguide.model.DivisionModel;
import kosbrother.com.doctorguide.presenter.ClickAddCommentPresenter;
import kosbrother.com.doctorguide.presenter.ClickAddDoctorPresenter;
import kosbrother.com.doctorguide.presenter.ClickProblemReportPresenter;
import kosbrother.com.doctorguide.presenter.ClickSharePresenter;
import kosbrother.com.doctorguide.presenter.DivisionFabPresenter;
import kosbrother.com.doctorguide.presenter.DivisionPresenter;
import kosbrother.com.doctorguide.view.ClickAddCommentView;
import kosbrother.com.doctorguide.view.ClickAddDoctorView;
import kosbrother.com.doctorguide.view.ClickProblemReportView;
import kosbrother.com.doctorguide.view.ClickShareView;
import kosbrother.com.doctorguide.view.DivisionFabView;
import kosbrother.com.doctorguide.view.DivisionView;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.AddDoctorViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionScoreViewModel;
import kosbrother.com.doctorguide.viewmodel.ProblemReportViewModel;

public class DivisionActivity extends GoogleSignInActivity implements
        DoctorFragment.OnListFragmentInteractionListener,
        DivisionScoreFragment.GetDivision,
        DivisionView,
        DivisionFabView,
        ClickAddDoctorView,
        ClickAddCommentView, ClickShareView, ClickProblemReportView {

    private DivisionPresenter divisionPresenter;
    private DivisionFabPresenter fabPresenter;
    private ClickAddCommentPresenter clickAddCommentPresenter;

    private ViewPagerAdapter adapter;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private ClickAddDoctorPresenter clickAddDoctorPresenter;
    private ClickSharePresenter clickSharePresenter;
    private ClickProblemReportPresenter clickProblemReportPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DivisionActivityViewModel viewModel = new DivisionActivityViewModel(getIntent());
        divisionPresenter = new DivisionPresenter(this, new DivisionModel(viewModel));
        divisionPresenter.onCreate();

        clickAddCommentPresenter = new ClickAddCommentPresenter(this, new ClickAddCommentModel(viewModel));
        clickAddDoctorPresenter = new ClickAddDoctorPresenter(this, new ClickAddDoctorModel(viewModel));
        clickSharePresenter = new ClickSharePresenter(this);
        clickProblemReportPresenter = new ClickProblemReportPresenter(this, new ClickProblemReportModel(viewModel));

        fabPresenter = new DivisionFabPresenter(this, new DivisionFabModel());
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_division);
    }

    @Override
    public void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("科別資訊");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setElevation(0);
    }

    @Override
    public void setDivisionImage(int divisionImageResId) {
        ((ImageView) findViewById(R.id.div_image)).setImageResource(divisionImageResId);
    }

    @Override
    public void setHospitalNameFromHtml(String htmlString) {
        ((TextView) findViewById(R.id.hospial_name)).setText(Html.fromHtml(htmlString));
    }

    @Override
    public void setDivisionScoreText(DivisionScoreViewModel divisionScoreViewModel) {
        ((TextView) findViewById(R.id.comment_num)).setText(divisionScoreViewModel.getCommentNumText());
        ((TextView) findViewById(R.id.recommend_num)).setText(divisionScoreViewModel.getRecommendNumText());
        ((TextView) findViewById(R.id.score)).setText(divisionScoreViewModel.getScoreAvgText());
    }

    @Override
    public void setupViewPager(int hospitalId, int divisionId) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DoctorFragment.newInstance(MyDoctorRecyclerViewAdapter.HEART_TYPE, hospitalId, divisionId), "科內醫生");
        adapter.addFragment(DivisionScoreFragment.newInstance(), "本科評分");
        adapter.addFragment(CommentFragment.newInstance(hospitalId, divisionId, null, GACategory.DIVISION), "本科評論");

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(onTabSelectedListener);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setupSpinner(List<String> divisionNames, String divisionName) {
        final Spinner spinner = (Spinner) findViewById(R.id.division_spinner);

        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, divisionNames);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaAdapter);
        final int originPosition = areaAdapter.getPosition(divisionName);
        spinner.setSelection(originPosition);
        spinner.post(new Runnable() {
            public void run() {
                spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (originPosition != position)
                            divisionPresenter.onDivisionSpinnerItemClick(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    @Override
    public void showCancelCollectDialog(String message) {
        new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                .setTitle("取消收藏")
                .setMessage(message)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        divisionPresenter.onConfirmCancelCollectClick();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showCollectSuccessSnackBar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.tabs), "成功收藏", Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
        snackbar.show();
    }

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
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void executeCancelCollectDoctor(final int doctorId) {
        Realm realm = Realm.getInstance(getBaseContext());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmDoctor doc = realm.where(RealmDoctor.class).equalTo("id", doctorId).findFirst();
                if (doc != null) {
                    doc.removeFromRealm();
                }
            }
        });
    }

    @Override
    public void executeCollectDoctor(final Doctor doctor, final String hospitalName, final int hospitalId) {
        Realm realm = Realm.getInstance(getBaseContext());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmDoctor realmDoctor = new RealmDoctor();
                realmDoctor.setId(doctor.id);
                realmDoctor.setName(doctor.name);
                realmDoctor.setAddress(doctor.address);
                realmDoctor.setHospital(hospitalName);
                realmDoctor.setHospitalId(hospitalId);
                realm.copyToRealmOrUpdate(realmDoctor);
            }
        });
    }

    @Override
    public void sendDivisionClickDivisionSpinnerEvent(String clickDivisionName) {
        GAManager.sendEvent(new DivisionClickDivisionSpinnerEvent(clickDivisionName));
    }

    @Override
    public void sendDivisionClickHospitalTextEvent(String hospitalName) {
        GAManager.sendEvent(new DivisionClickHospitalTextEvent(hospitalName));
    }

    @Override
    public void sendDivisionAddDoctorClickEvent(String label) {
        GAManager.sendEvent(new DivisionClickAddDoctorEvent(label));
    }

    @Override
    public void sendDivisionClickAddCommentEvent(String label) {
        GAManager.sendEvent(new DivisionClickAddCommentEvent(label));
    }

    @Override
    public void hideAddDoctorFab() {
        getAddDoctorFab().hide(true);
    }

    @Override
    public void showAddDoctorFab() {
        getAddDoctorFab().show(true);
    }

    @Override
    public void hideAddCommentFab() {
        getAddCommentFab().hide(true);
    }

    @Override
    public void showAddCommentFab() {
        getAddCommentFab().show(true);
    }

    private FloatingActionButton getAddCommentFab() {
        return (FloatingActionButton) findViewById(R.id.fab_add_comment);
    }

    private FloatingActionButton getAddDoctorFab() {
        return (FloatingActionButton) findViewById(R.id.fab_add_doctor);
    }

    public void sendClickFabEvent(String label) {
        GAManager.sendEvent(new DivisionClickFABEvent(label));
    }

    @Override
    public void startDoctorActivity(Doctor doctor, DivisionActivityViewModel viewModel) {
        Intent intent = new Intent(this, DoctorActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        intent.putExtra(ExtraKey.DOCTOR_ID, doctor.id);
        intent.putExtra(ExtraKey.DOCTOR_NAME, doctor.name);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        startActivity(intent);
    }

    @Override
    public void startDivisionActivity(DivisionActivityViewModel viewModel,
                                      int clickDivisionId, String clickDivisionName) {
        Intent intent = new Intent(DivisionActivity.this, DivisionActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.HOSPITAL_GRADE, viewModel.getHospitalGrade());
        intent.putExtra(ExtraKey.DIVISION_ID, clickDivisionId);
        intent.putExtra(ExtraKey.DIVISION_NAME, clickDivisionName);
        startActivity(intent);
    }

    @Override
    public void startHospitalActivity(DivisionActivityViewModel viewModel) {
        Intent intent = new Intent(this, HospitalActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        intent.putExtra(ExtraKey.HOSPITAL_GRADE, viewModel.getHospitalGrade());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        startActivity(intent);
    }

    public void startShareActivity() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void startCommentActivity(DivisionActivityViewModel viewModel, String email) {
        Intent intent = new Intent(this, AddCommentActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        intent.putExtra(ExtraKey.DIVISION_ID, viewModel.getDivisionId());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.USER, email);
        startActivity(intent);
    }

    public void dismissSignInDialog() {
        dialog.dismiss();
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
    public void onListFragmentInteraction(View view, final Doctor item) {
        if (view.getId() == R.id.heart) {
            divisionPresenter.onListFragmentHeartClick(item);
        } else {
            divisionPresenter.onListFragmentDoctorClick(item);
        }
    }

    public void onHospitalClick(View v) {
        divisionPresenter.onHospitalTextViewClick();
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
    public Division getDivision() {
        // TODO: 2016/3/3 need to refactoring
        return divisionPresenter.getDivision();
    }

    public void onAddCommentClick() {
        divisionPresenter.onAddCommentClick();
        clickAddCommentPresenter.startAddComment();
    }

    public void onAddDoctorClick() {
        divisionPresenter.onAddDoctorClick();
        clickAddDoctorPresenter.startAddDoctor();
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
    public void startAddDoctorActivity(AddDoctorViewModel addDoctorViewModel) {
        Intent intent = new Intent(DivisionActivity.this, AddDoctorActivity.class);
        intent.putExtra(ExtraKey.HOSPITAL_NAME, addDoctorViewModel.getHospitalName());
        intent.putExtra(ExtraKey.DIVISION_NAME, addDoctorViewModel.getDivisionName());
        intent.putExtra(ExtraKey.HOSPITAL_ID, addDoctorViewModel.getHospitalId());
        intent.putExtra(ExtraKey.DIVISION_ID, addDoctorViewModel.getDivisionId());
        startActivity(intent);
    }

    public void onFabAddCommentClick(View view) {
        fabPresenter.onFabCommentClick();
        clickAddCommentPresenter.startAddComment();
    }

    public void onFabAddDoctorClick(View view) {
        fabPresenter.onFabAddDoctorClick();
        clickAddDoctorPresenter.startAddDoctor();
    }

    @Override
    public void startProblemReportActivity(ProblemReportViewModel viewModel) {
        Intent intent = new Intent(this, ProblemReportActivity.class);
        intent.putExtra(ExtraKey.REPORT_TYPE, viewModel.getReportType());
        intent.putExtra(ExtraKey.HOSPITAL_NAME, viewModel.getHospitalName());
        intent.putExtra(ExtraKey.DIVISION_NAME, viewModel.getDivisionName());
        intent.putExtra(ExtraKey.DIVISION_ID, viewModel.getDivisionId());
        intent.putExtra(ExtraKey.HOSPITAL_ID, viewModel.getHospitalId());
        startActivity(intent);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            onPageChanged(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            onPageChanged(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    private void onPageChanged(int position) {
        fabPresenter.onPageChanged(position);
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

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}

