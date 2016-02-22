package kosbrother.com.doctorguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kosbrother.com.doctorguide.CommentDetailActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.division.DivisionClickCommentListEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickCommentListEvent;
import kosbrother.com.doctorguide.google_analytics.event.hospital.HospitalClickCommentListEvent;
import kosbrother.com.doctorguide.google_analytics.event.mycomment.MyCommentClickCommentListEvent;

/**
 * Created by steven on 12/26/15.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {


    private final ArrayList<Comment> mComments;
    private final Context mContext;
    private final String mGACategory;

    public CommentAdapter(ArrayList<Comment> comments, Context context, String gACategory) {
        mComments = comments;
        mContext = context;
        mGACategory = gACategory;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentAdapter.CommentViewHolder holder, final int position) {
        final Comment comment = mComments.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mGACategory) {
                    case GACategory.DOCTOR:
                        GAManager.sendEvent(new DoctorClickCommentListEvent(comment));
                        break;
                    case GACategory.DIVISION:
                        GAManager.sendEvent(new DivisionClickCommentListEvent(comment));
                        break;
                    case GACategory.HOSPITAL:
                        GAManager.sendEvent(new HospitalClickCommentListEvent(comment));
                        break;
                    case GACategory.MY_COMMENT:
                        GAManager.sendEvent(new MyCommentClickCommentListEvent(comment));
                        break;
                }

                Intent intent = new Intent(mContext, CommentDetailActivity.class);
                intent.putExtra("COMMENT_ID", comment.id);
                mContext.startActivity(intent);
            }
        });
        holder.mDivisionName.setText(comment.division_name);
        if (mGACategory.equals(GACategory.MY_COMMENT)) {
            holder.mCommenterName.setVisibility(View.GONE);
        } else {
            holder.mCommenterName.setText(comment.user_name);
        }
        holder.mhospialName.setText(comment.hospital_name);
        holder.mDoctor.setText(comment.doctor_name);
        if (comment.is_recommend) {
            holder.mRecommend.setVisibility(View.VISIBLE);
        } else {
            holder.mRecommend.setVisibility(View.INVISIBLE);
        }
        float divScore = (comment.div_environment + comment.div_equipment + comment.div_friendly + comment.div_speciality) / 4.0f;
        holder.mDivScore.setText(String.format("%.1f", divScore));
        holder.mDivRaitng.setRating(divScore);
        DrawableCompat.setTint(holder.mDivRaitng.getProgressDrawable(), ContextCompat.getColor(mContext, R.color.rating_bar_color));

        float drScore = (comment.dr_friendly + comment.dr_speciality) / 2.0f;
        holder.mDrScore.setText(String.format("%.1f", drScore));
        holder.mDrRaitng.setRating(drScore);
        DrawableCompat.setTint(holder.mDrRaitng.getProgressDrawable(), ContextCompat.getColor(mContext, R.color.rating_bar_color));

        holder.mCommentTime.setText(new SimpleDateFormat("yyyy/MM/dd").format(comment.updated_at));
        if (comment.div_comment == null || comment.div_comment.equals(""))
            holder.mDivComment.setText("暫無評論");
        else
            holder.mDivComment.setText(comment.div_comment);

        if (comment.dr_comment == null || comment.dr_comment.equals(""))
            holder.mDrComment.setText("暫無評論");
        else
            holder.mDrComment.setText(comment.dr_comment);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView mDivisionName;
        public TextView mCommenterName;
        public TextView mhospialName;
        public TextView mDoctor;
        public ImageView mRecommend;
        public TextView mDivScore;
        public RatingBar mDivRaitng;
        public TextView mDrScore;
        public RatingBar mDrRaitng;
        public TextView mCommentTime;
        public TextView mDivComment;
        public TextView mDrComment;

        CommentViewHolder(View view) {
            super(view);
            mView = view;
            mhospialName = (TextView) view.findViewById(R.id.mHostital);
            mDivisionName = (TextView) view.findViewById(R.id.division);
            mCommenterName = (TextView) view.findViewById(R.id.commenterNameTextView);
            mDoctor = (TextView) view.findViewById(R.id.doctor);
            mRecommend = (ImageView) view.findViewById(R.id.awrad);
            mDivScore = (TextView) view.findViewById(R.id.div_score);
            mDivRaitng = (RatingBar) view.findViewById(R.id.div_score_rating);
            mDrScore = (TextView) view.findViewById(R.id.dr_score);
            mDrRaitng = (RatingBar) view.findViewById(R.id.dr_score_rating);
            mCommentTime = (TextView) view.findViewById(R.id.comment_time);
            mDivComment = (TextView) view.findViewById(R.id.div_comment);
            mDrComment = (TextView) view.findViewById(R.id.dr_comment);
        }
    }
}
