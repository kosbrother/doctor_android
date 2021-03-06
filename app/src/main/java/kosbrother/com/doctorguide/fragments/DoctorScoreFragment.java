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

import kosbrother.com.doctorguide.DoctorActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Doctor;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorScoreFragment extends Fragment {


    private static Doctor mDoctor;

    public DoctorScoreFragment() {
        // Required empty public constructor
    }

    public static DoctorScoreFragment newInstance() {
        DoctorScoreFragment fragment = new DoctorScoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(mDoctor != null && mDoctor.comment_num > 0) {
            View view = inflater.inflate(R.layout.fragment_doctor_score, container, false);
            ((TextView)view.findViewById(R.id.review_count)).setText(mDoctor.comment_num + "");
            ((TextView)view.findViewById(R.id.avg_text)).setText("綜合滿意度 " + String.format("%.1f", mDoctor.avg));
            ((TextView)view.findViewById(R.id.friend_score)).setText(String.format("%.1f", mDoctor.avg_friendly));
            ((TextView)view.findViewById(R.id.spe_score)).setText(String.format("%.1f", mDoctor.avg_speciality));
            RatingBar avgRating = (RatingBar) view.findViewById(R.id.avg_rating);
            RatingBar friendRatingBar = (RatingBar) view.findViewById(R.id.friend_rating_bar);
            RatingBar speRatingBar = (RatingBar) view.findViewById(R.id.spe_rating_bar);
            avgRating.setRating(mDoctor.avg);
            friendRatingBar.setRating(mDoctor.avg_friendly);
            speRatingBar.setRating(mDoctor.avg_speciality);
            DrawableCompat.setTint(avgRating.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));
            DrawableCompat.setTint(friendRatingBar.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));
            DrawableCompat.setTint(speRatingBar.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.rating_bar_color));

            return view;
        }else{
            View view = inflater.inflate(R.layout.fragment_no_score, container, false);
            view.findViewById(R.id.add_comment_linear_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoctorActivity)getActivity()).onAddCommentClick();
                }
            });
            return view;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GetDoctor) {
            mDoctor = ((GetDoctor) context).getDodctor();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GetDoctor");
        }
    }

    public interface GetDoctor{
        Doctor getDodctor();
    }

}
