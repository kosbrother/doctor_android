package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import kosbrother.com.doctorguide.Util.SignInActivity;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CommentAdapter;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.mycomment.MyCommentClickFacebookSignInEvent;
import kosbrother.com.doctorguide.google_analytics.event.mycomment.MyCommentClickGoogleSignInEvent;
import kosbrother.com.doctorguide.model.MyCommentModel;
import kosbrother.com.doctorguide.presenter.MyCommentPresenter;
import kosbrother.com.doctorguide.view.MyCommentView;

public class MyCommentActivity extends SignInActivity implements
        MyCommentView {

    private ProgressDialog mProgressDialog;

    private MyCommentPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MyCommentPresenter(this, new MyCommentModel());
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
    public void showProgressDialog() {
        mProgressDialog = Util.showProgressDialog(MyCommentActivity.this);
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    protected void afterCreateUserSuccess() {
        presenter.afterCreateUserSuccess();
    }

    @Override
    protected void sendGoogleSignInEvent() {
        GAManager.sendEvent(new MyCommentClickGoogleSignInEvent());
    }

    @Override
    protected void sendFacebookSignInEvent() {
        GAManager.sendEvent(new MyCommentClickFacebookSignInEvent());
    }

}
