package kosbrother.com.doctorguide.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Division;

/**
 * A simple {@link Fragment} subclass.
 */
public class DivisionScoreFragment extends Fragment {


    private static Division mDivision;

    public DivisionScoreFragment() {
        // Required empty public constructor
    }

    public static DivisionScoreFragment newInstance() {
        DivisionScoreFragment fragment = new DivisionScoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(mDivision != null && mDivision.comment_num > 0) {
            View view = inflater.inflate(R.layout.fragment_division_score, container, false);

            ((TextView)view.findViewById(R.id.dr_avg_text)).setText("綜合滿意度 " + String.format("%.1f", mDivision.dr_avg_score));
            ((TextView)view.findViewById(R.id.friend_score)).setText(String.format("%.1f", mDivision.dr_avg_friendly));
            ((TextView)view.findViewById(R.id.spe_score)).setText(String.format("%.1f", mDivision.dr_avg_speciality));
            RatingBar avgRating = (RatingBar) view.findViewById(R.id.dr_score_rating);
            RatingBar friendRatingBar = (RatingBar) view.findViewById(R.id.friend_rating_bar);
            RatingBar speRatingBar = (RatingBar) view.findViewById(R.id.spe_rating_bar);

            avgRating.setRating(mDivision.dr_avg_score);
            friendRatingBar.setRating(mDivision.dr_avg_friendly);
            speRatingBar.setRating(mDivision.dr_avg_speciality);

            DrawableCompat.setTint(avgRating.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));
            DrawableCompat.setTint(friendRatingBar.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));
            DrawableCompat.setTint(speRatingBar.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));

            ((TextView)view.findViewById(R.id.review_count)).setText(mDivision.comment_num + "");
            ((TextView)view.findViewById(R.id.avg_text)).setText("綜合滿意度 " + String.format("%.1f", mDivision.avg));
            ((TextView)view.findViewById(R.id.env_score)).setText(String.format("%.1f", mDivision.avg_environment));
            RatingBar envRating = (RatingBar) view.findViewById(R.id.env_rating_bar);
            envRating.setRating(mDivision.avg_environment);
            DrawableCompat.setTint(envRating.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));

            ((TextView)view.findViewById(R.id.equipment_score)).setText(String.format("%.1f", mDivision.avg_equipment));
            RatingBar equitmentRating = (RatingBar) view.findViewById(R.id.equipment_rating_bar);
            equitmentRating.setRating(mDivision.avg_equipment);
            DrawableCompat.setTint(equitmentRating.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));

            ((TextView)view.findViewById(R.id.spe_score_div)).setText(String.format("%.1f", mDivision.avg_speciality));
            RatingBar divSpeRating = (RatingBar) view.findViewById(R.id.spe_div_rating_bar);
            divSpeRating.setRating(mDivision.avg_speciality);
            DrawableCompat.setTint(divSpeRating.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));

            ((TextView)view.findViewById(R.id.friend_score_div)).setText(String.format("%.1f", mDivision.avg_friendly));
            RatingBar divFriendlyRating = (RatingBar) view.findViewById(R.id.friend_div_rating_bar);
            equitmentRating.setRating(mDivision.avg_friendly);
            DrawableCompat.setTint(divFriendlyRating.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));

            return view;
        } else
            return inflater.inflate(R.layout.fragment_no_score, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GetDivision) {
            mDivision = ((GetDivision) context).getDivision();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GetDivision");
        }
    }

    public interface GetDivision{
        Division getDivision();
    }

}
