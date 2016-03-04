package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CategoryAdapter;
import kosbrother.com.doctorguide.entity.Category;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickAccountEvent;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickSearchIconEvent;
import kosbrother.com.doctorguide.google_analytics.event.main.MainSubmitSearchTextEvent;
import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.task.CreateUserTask;

public class MainActivity extends GoogleSignInActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        CreateUserTask.CreateUserListener {

    RecyclerView mRecyclerView;
    private SignInButton signInBtn;
    private TextView logInEmail;
    private DrawerLayout drawer;
    private User user;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        toolbar.setNavigationIcon(R.mipmap.ic_toolbar_logo);
        // need overwrite unless it will try open left drawler then crash
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, r.getDisplayMetrics());
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) px);
        header.setLayoutParams(lparams);
        navigationView.addHeaderView(header);

        logInEmail = (TextView) header.findViewById(R.id.log_in_email);
        signInBtn = (SignInButton) header.findViewById(R.id.sign_in_button);

        setRecyclerView();

        signInBtn.setSize(SignInButton.SIZE_WIDE);
        signInBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAManager.sendEvent(new MainClickAccountEvent(GALabel.SIGN_IN));

                signIn();
            }
        });
    }

    @Override
    protected void handleSignInResult(GoogleSignInResult result) {
        super.handleSignInResult(result);
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            user = new User();
            if (acct != null) {
                user.email = acct.getEmail();
                user.name = acct.getDisplayName();
                if (acct.getPhotoUrl() != null)
                    user.pic_url = acct.getPhotoUrl().toString();
            }
            mProgressDialog = Util.showProgressDialog(this);
            new CreateUserTask(this).execute(user);
        } else {
            drawNavigationSignInPart(false);
        }
    }

    @Override
    public void onCreateUserSuccess() {
        mProgressDialog.dismiss();
        drawNavigationSignInPart(true);
    }

    @Override
    public void onCreateUserFail() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
    }

    private void drawNavigationSignInPart(boolean signedIn) {
        if (signedIn) {
            logInEmail.setText(user.name);
            logInEmail.setVisibility(View.VISIBLE);
            signInBtn.setVisibility(View.GONE);
        } else {
            logInEmail.setVisibility(View.GONE);
            signInBtn.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerView() {
//        String[] myStringArray = {"家醫科", "家醫科", "內科", "家醫科", "家醫科", "內科", "家醫科", "家醫科", "內科", "家醫科", "家醫科", "內科"};
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CategoryAdapter(this, Category.getCategories()));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GAManager.sendEvent(new MainClickSearchIconEvent());
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GAManager.sendEvent(new MainSubmitSearchTextEvent(query));

                searchView.onActionViewCollapsed();
                searchView.setQuery("", false);
                searchView.clearFocus();
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
        int id = item.getItemId();

        if (id == R.id.account) {
            GAManager.sendEvent(new MainClickAccountEvent(GALabel.MENU_ITEM));

            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_collections) {
            GAManager.sendEvent(new MainClickAccountEvent(GALabel.MY_COLLECTION));

            Intent intent = new Intent(this, MyCollectionActivity.class);
            startActivity(intent);
        } else if (id == R.id.my_comments) {
            GAManager.sendEvent(new MainClickAccountEvent(GALabel.MY_COMMENT));

            Intent intent = new Intent(this, MyCommentActivity.class);
            if (user != null)
                intent.putExtra(ExtraKey.USER_EMAIL, user.email);
            startActivity(intent);
        } else if (id == R.id.setting) {
            GAManager.sendEvent(new MainClickAccountEvent(GALabel.SETTING));

            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.feedback) {
            GAManager.sendEvent(new MainClickAccountEvent(GALabel.FEEDBACK));

            Intent intent = new Intent(this, FeedbackActivity.class);
            startActivity(intent);
        } else if (id == R.id.play_store) {
            GAManager.sendEvent(new MainClickAccountEvent(GALabel.PLAY_STORE));

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this, getString(R.string.login_fail), Toast.LENGTH_LONG).show();
    }

}
