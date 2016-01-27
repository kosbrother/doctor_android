package kosbrother.com.doctorguide.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CommentAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;


public class CommentFragment extends Fragment {

    private static final String ARG_HOSPITAL = "param1";
    private static final String ARG_DIVISION = "param2";

    private Integer mHospitalId;
    private Integer mDivisionId;

    private ArrayList<Comment> comments;
    private View view;


    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(Integer hospital_id,Integer division_id) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        if(hospital_id != null)
            args.putInt(ARG_HOSPITAL, hospital_id);
        if(division_id != null)
            args.putInt(ARG_DIVISION, division_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHospitalId = getArguments().getInt(ARG_HOSPITAL);
            mDivisionId = getArguments().getInt(ARG_DIVISION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_comment, container, false);
        new GetCommentTask().execute();
        return view;
    }

    private class GetCommentTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Util.showProgressDialog(getContext());
        }
        @Override
        protected Object doInBackground(Object... params) {
            if(mDivisionId != 0 && mHospitalId != 0)
                comments = DoctorGuideApi.getDivisionComments(mDivisionId,mHospitalId);
            else if(mHospitalId != 0)
                comments = DoctorGuideApi.getHospitalComments(mHospitalId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            Util.hideProgressDialog();
            if(comments.size() == 0){
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                View noDoctorView = inflater.inflate(R.layout.fragment_no_comment, null);
                noDoctorView.setLayoutParams(lparams);
                ((RelativeLayout) view.findViewById(R.id.baseLayout)).addView(noDoctorView);
            }else{
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                Context context = view.getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new CommentAdapter(comments, getContext()));
            }
        }

    }

}
