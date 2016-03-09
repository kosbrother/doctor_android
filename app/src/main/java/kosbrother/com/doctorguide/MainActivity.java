package kosbrother.com.doctorguide;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CategoryAdapter;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickAccountEvent;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickSearchIconEvent;
import kosbrother.com.doctorguide.google_analytics.event.main.MainSubmitSearchTextEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.model.MainModel;
import kosbrother.com.doctorguide.presenter.MainPresenter;
import kosbrother.com.doctorguide.view.MainView;

public class MainActivity extends GoogleSignInActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        MainView {

    private SignInButton signInBtn;
    private TextView logInEmail;
    private DrawerLayout drawer;
    private ProgressDialog mProgressDialog;

    private MainPresenter presenter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenter(this, new MainModel());
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
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
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setToolBarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.mipmap.ic_toolbar_logo);
        // need overwrite unless it will try open left drawer then crash
        toolbar.setNavigationOnClickListener(null);
    }

    @SuppressLint("InflateParams")
    @Override
    public void setNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, r.getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) px);
        header.setLayoutParams(layoutParams);
        navigationView.addHeaderView(header);

        logInEmail = (TextView) header.findViewById(R.id.log_in_email);
        signInBtn = (SignInButton) header.findViewById(R.id.sign_in_button);
        signInBtn.setSize(SignInButton.SIZE_WIDE);
        signInBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSignInButtonClick();
            }
        });
    }

    @Override
    public void setAppVersionName(String versionName) {
        ((TextView) findViewById(R.id.version_name_text_view)).setText(versionName);
    }

    @Override
    public void setRecyclerView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CategoryAdapter(this, Category.getCategories()));
    }

    @Override
    public void setUserName(String userName) {
        logInEmail.setText(GoogleSignInManager.getInstance().getName());
    }

    @Override
    public void initSearchView() {
        searchView.onActionViewCollapsed();
        searchView.setQuery("", false);
        searchView.clearFocus();
    }

    @Override
    public void showUserName() {
        logInEmail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUserName() {
        logInEmail.setVisibility(View.GONE);
    }

    @Override
    public void hideSignInButton() {
        signInBtn.setVisibility(View.GONE);
    }

    @Override
    public void showSignInButton() {
        signInBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.END);
    }

    @Override
    public void toggleDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            drawer.openDrawer(GravityCompat.END);
        }
    }

    @Override
    public void showCreateUserFailToast() {
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showConnectionFailedToast() {
        Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_LONG).show();
    }

    @Override
    public void sendMainClickAccountEvent(String label) {
        GAManager.sendEvent(new MainClickAccountEvent(GALabel.SIGN_IN));
    }

    @Override
    public void sendMainClickSearchIconEvent() {
        GAManager.sendEvent(new MainClickSearchIconEvent());
    }

    @Override
    public void sendMainSubmitSearchTextEvent(String query) {
        GAManager.sendEvent(new MainSubmitSearchTextEvent(query));
    }

    @Override
    public void startMyCollectionActivity() {
        startActivity(new Intent(this, MyCollectionActivity.class));
    }

    @Override
    public void startMyCommentActivity(String userName) {
        Intent intent = new Intent(this, MyCommentActivity.class);
        intent.putExtra(ExtraKey.USER_EMAIL, userName);
        startActivity(intent);
    }

    @Override
    public void startSettingActivity() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void startFeedbackActivity() {
        startActivity(new Intent(this, FeedbackActivity.class));
    }

    @Override
    protected void handleSignInResult(GoogleSignInResult result) {
        super.handleSignInResult(result);
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            presenter.onSignInSuccess();
        } else {
            presenter.onSignInFail();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            presenter.onBackPressedWhenDrawerOpen();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSearchViewClick();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.onQueryTextSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.account) {
            presenter.onAccountMenuItemSelected();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.getItemId() == R.id.my_collections) {
            presenter.onNavigationMyCollectionsClick();
        } else if (item.getItemId() == R.id.my_comments) {
            presenter.onNavigationMyCommentClick();
        } else if (item.getItemId() == R.id.setting) {
            presenter.onNavigationSettingClick();
        } else if (item.getItemId() == R.id.feedback) {
            presenter.onNavigationFeedbackClick();
        } else if (item.getItemId() == R.id.play_store) {
            presenter.onNavigationPlayStoreClick();
        }

        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        presenter.onConnectionFailed();
    }
}
