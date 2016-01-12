package kosbrother.com.doctorguide.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        if(n == 1)
            return inflater.inflate(R.layout.fragment_division_score, container, false);
        else
            return inflater.inflate(R.layout.fragment_no_score_in_division, container, false);
    }

}
