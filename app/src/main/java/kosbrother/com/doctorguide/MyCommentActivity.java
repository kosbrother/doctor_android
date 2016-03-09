package kosbrother.com.doctorguide;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CommentAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;

public class MyCommentActivity extends GoogleSignInActivity implements
        CreateUserTask.CreateUserListener {

    private ActionBar actionbar;
    RecyclerView mRecyclerView;
    private String userEmail;
    private ArrayList<Comment> mComments;
    private LinearLayout noCommentLayout;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment);

        actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setTitle("我的評論");
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userEmail = extras.getString(ExtraKey.USER_EMAIL);
        }

        if (userEmail == null) {
            final Dialog dialog = new Dialog(MyCommentActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_login_enter_mycomment);

            SignInButton signInBtn = (SignInButton) dialog.findViewById(R.id.sign_in_button);
            signInBtn.setSize(SignInButton.SIZE_WIDE);
            signInBtn.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isNetworkConnected()) {
                        showRequireNetworkDialog();
                        return;
                    }
                    signIn();
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            new GetMyCommentsTask().execute();
        }

        noCommentLayout = (LinearLayout) findViewById(R.id.no_comment_layout);
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
            if (mComments.size() == 0)
                noCommentLayout.setVisibility(View.VISIBLE);
            setRecyclerView();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                mProgressDialog = Util.showProgressDialog(MyCommentActivity.this);
                new CreateUserTask(this).execute(GoogleSignInManager.getInstance().getUser());
            }
        }
    }

    @Override
    public void onCreateUserSuccess() {
        mProgressDialog.dismiss();
        new GetMyCommentsTask().execute();
    }

    @Override
    public void onCreateUserFail() {
        mProgressDialog.dismiss();
        Toast.makeText(this, "登入失敗", Toast.LENGTH_SHORT).show();
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommentAdapter(mComments, this, GACategory.MY_COMMENT));
    }

}
