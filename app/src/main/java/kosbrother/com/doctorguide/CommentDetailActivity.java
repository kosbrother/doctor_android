package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.model.CommentDetailModelImpl;
import kosbrother.com.doctorguide.presenter.CommentDetailPresenter;
import kosbrother.com.doctorguide.view.CommentDetailView;
import kosbrother.com.doctorguide.viewmodel.CommentInfoViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorCommentViewModel;

public class CommentDetailActivity extends AppCompatActivity implements CommentDetailView {

    private CommentDetailPresenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CommentDetailPresenter(this, new CommentDetailModelImpl(getCommentId()));
        presenter.onCreate();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_comment_detail);
    }

    @Override
    public void initActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("評論詳情");
        actionbar.setDisplayHomeAsUpEnabled(true);
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
    public void setCommentInfo(CommentInfoViewModel commentInfoViewModel) {
        ((TextView) findViewById(R.id.commenter)).setText(commentInfoViewModel.getCommenter());
        ((TextView) findViewById(R.id.comment_time)).setText(commentInfoViewModel.getCommentTime());
        ((TextView) findViewById(R.id.hospital)).setText(commentInfoViewModel.getHospital());
        ((TextView) findViewById(R.id.division)).setText(commentInfoViewModel.getDivision());
        ((TextView) findViewById(R.id.doctor)).setText(commentInfoViewModel.getDoctor());
        // TODO: 2016/3/2 add 就診時間 next version
    }

    @Override
    public void setDivisionComment(DivisionCommentViewModel divisionCommentViewModel) {
        ((TextView) findViewById(R.id.env_score)).setText(divisionCommentViewModel.getEnvScoreText());
        setRatingBar((RatingBar) findViewById(R.id.env_rating_bar), divisionCommentViewModel.getEnvScore());

        ((TextView) findViewById(R.id.equipment_score)).setText(divisionCommentViewModel.getEquipmentScoreText());
        setRatingBar((RatingBar) findViewById(R.id.equipment_rating_bar), divisionCommentViewModel.getEquipmentScore());

        ((TextView) findViewById(R.id.spe_score_div)).setText(divisionCommentViewModel.getSpeScoreText());
        setRatingBar((RatingBar) findViewById(R.id.spe_div_rating_bar), divisionCommentViewModel.getSpeScore());

        ((TextView) findViewById(R.id.friend_score_div)).setText(divisionCommentViewModel.getFriendScoreText());
        setRatingBar((RatingBar) findViewById(R.id.friend_div_rating_bar), divisionCommentViewModel.getFriendScore());

        ((TextView) findViewById(R.id.div_comment)).setText(divisionCommentViewModel.getCommentText());
    }

    @Override
    public void setDoctorComment(DoctorCommentViewModel doctorCommentViewModel) {
        ((TextView) findViewById(R.id.doctor_friend_score)).setText(doctorCommentViewModel.getFriendScoreText());
        setRatingBar((RatingBar) findViewById(R.id.doctor_friend_rating_bar), doctorCommentViewModel.getFriendScore());

        ((TextView) findViewById(R.id.spe_score)).setText(doctorCommentViewModel.getSpecialtyText());
        setRatingBar((RatingBar) findViewById(R.id.spe_rating_bar), doctorCommentViewModel.getSpecialty());

        findViewById(R.id.award).setVisibility(doctorCommentViewModel.isRecommend() ? View.VISIBLE : View.INVISIBLE);

        ((TextView) findViewById(R.id.dr_comment)).setText(doctorCommentViewModel.getComment());
    }

    private void setRatingBar(RatingBar speRatingBar, int rating) {
        speRatingBar.setRating(rating);
        DrawableCompat.setTint(speRatingBar.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) presenter.onHomeMenuItemSelected();
        return true;
    }

    private int getCommentId() {
        int commentId = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            commentId = extras.getInt("COMMENT_ID");
        }
        return commentId;
    }
}
