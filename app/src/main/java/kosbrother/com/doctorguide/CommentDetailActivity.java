package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;

public class CommentDetailActivity extends AppCompatActivity {

    private ActionBar actionbar;
    private int commentId;
    private Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            commentId = extras.getInt("COMMENT_ID");
        }

        actionbar = getSupportActionBar();
        actionbar.setTitle("評論詳情");
        actionbar.setDisplayHomeAsUpEnabled(true);

        new GetCommentTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.comment_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
//            case R.id.edit:
//                Intent intent = new Intent(this, AddCommentActivity.class);
//                intent.putExtra("DOCTOR_ID",comment.doctor_id);
//                intent.putExtra("HOSPITAL_NAME",comment.hospital_name);
//                intent.putExtra("HOSPITAL_ID",comment.hospital_id);
//                startActivity(intent);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetCommentTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(CommentDetailActivity.this);
        }
        @Override
        protected Object doInBackground(Object... params) {
            comment = DoctorGuideApi.getComment(commentId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            setViews();
        }

    }

    private void setViews() {
        TextView mCommentTime = (TextView) findViewById(R.id.comment_time);
        TextView mhospialName = (TextView) findViewById(R.id.mHostital);
        TextView mDivisionName = (TextView) findViewById(R.id.division);
        TextView mDoctor = (TextView) findViewById(R.id.doctor);
        ImageView mRecommend = (ImageView) findViewById(R.id.awrad);
        TextView mDivComment = (TextView) findViewById(R.id.div_comment);
        TextView mDrComment = (TextView) findViewById(R.id.dr_comment);
        TextView mCommentor = (TextView) findViewById(R.id.commentor);

        mCommentor.setText(comment.user_name);

        ((TextView)findViewById(R.id.friend_score)).setText(String.format("%d", comment.dr_friendly));
        RatingBar friendRatingBar = (RatingBar)findViewById(R.id.friend_rating_bar);
        friendRatingBar.setRating(comment.dr_friendly);
        DrawableCompat.setTint(friendRatingBar.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));

        ((TextView) findViewById(R.id.spe_score)).setText(String.format("%d", comment.dr_speciality));
        RatingBar speRatingBar = (RatingBar)findViewById(R.id.spe_rating_bar);
        speRatingBar.setRating(comment.dr_speciality);
        DrawableCompat.setTint(speRatingBar.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));

        ((TextView)findViewById(R.id.env_score)).setText(String.format("%d", comment.div_environment));
        RatingBar envRating = (RatingBar)findViewById(R.id.env_rating_bar);
        envRating.setRating(comment.div_environment);
        DrawableCompat.setTint(envRating.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));

        ((TextView)findViewById(R.id.equipment_score)).setText(String.format("%d", comment.div_equipment));
        RatingBar equitmentRating = (RatingBar)findViewById(R.id.equipment_rating_bar);
        equitmentRating.setRating(comment.div_equipment);
        DrawableCompat.setTint(equitmentRating.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));

        ((TextView)findViewById(R.id.spe_score_div)).setText(String.format("%d", comment.div_speciality));
        RatingBar divSpeRating = (RatingBar)findViewById(R.id.spe_div_rating_bar);
        divSpeRating.setRating(comment.div_speciality);
        DrawableCompat.setTint(divSpeRating.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));

        ((TextView)findViewById(R.id.friend_score_div)).setText(String.format("%d", comment.div_friendly));
        RatingBar divFriendlyRating = (RatingBar)findViewById(R.id.friend_div_rating_bar);
        divFriendlyRating.setRating(comment.div_friendly);
        DrawableCompat.setTint(divFriendlyRating.getProgressDrawable(), ContextCompat.getColor(this, R.color.rating_bar_color));


        mDivisionName.setText(comment.division_name);
        mhospialName.setText(comment.hospital_name);
        mDoctor.setText(comment.doctor_name);
        if(comment.is_recommend){
            mRecommend.setVisibility(View.VISIBLE);
        }else{
            mRecommend.setVisibility(View.INVISIBLE);
        }

        if (comment.dr_comment == null || comment.dr_comment.equals(""))
            mDrComment.setText("暫無評論");
        else
            mDrComment.setText(comment.dr_comment);

        if (comment.div_comment == null || comment.div_comment.equals(""))
            mDivComment.setText("暫無評論");
        else
            mDivComment.setText(comment.div_comment);

        mCommentTime.setText(new SimpleDateFormat("yyyy/MM/dd").format(comment.updated_at));
    }
}
