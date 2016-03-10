package kosbrother.com.doctorguide;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.GoogleSignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CommentAdapter;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.model.MyCommentModel;
import kosbrother.com.doctorguide.presenter.MyCommentPresenter;
import kosbrother.com.doctorguide.view.MyCommentView;

public class MyCommentActivity extends GoogleSignInActivity implements
        MyCommentView {

    private ProgressDialog mProgressDialog;

    private MyCommentPresenter presenter;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyCommentModel model = new MyCommentModel(getUserEmail());
        presenter = new MyCommentPresenter(this, model);
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_my_comment);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("我的評論");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showSingInDialog() {
        dialog = new Dialog(MyCommentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login_enter_mycomment);

        SignInButton signInBtn = (SignInButton) dialog.findViewById(R.id.sign_in_button);
        signInBtn.setSize(SignInButton.SIZE_WIDE);
        signInBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSignInButtonClick();
            }
        });
        dialog.show();
    }

    @Override
    public void showNoCommentLayout() {
        findViewById(R.id.no_comment_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void setRecyclerView(ArrayList<Comment> comments) {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommentAdapter(comments, this, GACategory.MY_COMMENT));
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
    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(MyCommentActivity.this);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                presenter.onSignInSuccess();
            }
        }
    }

    private String getUserEmail() {
        String userEmail = null;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                userEmail = extras.getString(ExtraKey.USER_EMAIL);
            }
        }
        return userEmail;
    }
}
