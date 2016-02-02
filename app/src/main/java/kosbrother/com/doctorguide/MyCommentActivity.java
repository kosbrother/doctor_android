package kosbrother.com.doctorguide;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.CreateUserTask;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CommentAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.entity.User;

public class MyCommentActivity extends GoogleSignInActivity implements CreateUserTask.AfterCreateUser{

    private ActionBar actionbar;
    RecyclerView mRecyclerView;
    private String userEmail;
    private ArrayList<Comment> mComments;
    private LinearLayout noCommentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);

        actionbar = getSupportActionBar();
        actionbar.setTitle("我的評論");
        actionbar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString("USER_EMAIL");
        }

        if(userEmail == null) {
            final Dialog dialog = new Dialog(MyCommentActivity.this);
            dialog.setContentView(R.layout.dialog_login_enter_mycomment);

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
        }else{
            new GetMyCommentsTask().execute();
        }

        noCommentLayout = (LinearLayout)findViewById(R.id.no_comment_layout);
    }

    private class GetMyCommentsTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(MyCommentActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            mComments = DoctorGuideApi.getUserComments(userEmail);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if(mComments.size() == 0)
                noCommentLayout.setVisibility(View.VISIBLE);
            setRecyclerView();
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
            userEmail = acct.getEmail();
            user.name = acct.getDisplayName();
            if(acct.getPhotoUrl() != null)
                user.pic_url = acct.getPhotoUrl().toString();
            new CreateUserTask(this,user).execute();
        }
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommentAdapter(mComments,this));
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

    @Override
    public void afterCreateUser() {
        new GetMyCommentsTask().execute();
    }
}
