package kosbrother.com.doctorguide.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import java.util.Random;

import kosbrother.com.doctorguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DivisionScoreFragment extends Fragment {


    public DivisionScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Random rand = new Random();
        int n = rand.nextInt(2);

        if(n == 1) {
            View view = inflater.inflate(R.layout.fragment_division_score, container, false);
            RatingBar r = (RatingBar) view.findViewById(R.id.i_rating);
            DrawableCompat.setTint(r.getProgressDrawable(), ContextCompat.getColor(getContext(), R.color.tab_text_act));
            return view;
        } else
            return inflater.inflate(R.layout.fragment_no_score, container, false);
    }

}
