package kosbrother.com.doctorguide.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kosbrother.com.doctorguide.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDivisionCommentFragment extends Fragment {


    public AddDivisionCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_division_comment, container, false);
    }

}
