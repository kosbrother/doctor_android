package kosbrother.com.doctorguide.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import kosbrother.com.doctorguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorScoreFragment extends Fragment {


    public DoctorScoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Random rand = new Random();
        int n = rand.nextInt(2);

        if(n == 1)
            return inflater.inflate(R.layout.fragment_doctor_score, container, false);
        else{
            View view = inflater.inflate(R.layout.fragment_no_score, container, false);
            ((TextView)view.findViewById(R.id.content)).setText("歡迎您為這位醫師評分，您寶貴的意見能讓台灣的醫療品質變得更好!!");
            return view;
        }

    }

}
