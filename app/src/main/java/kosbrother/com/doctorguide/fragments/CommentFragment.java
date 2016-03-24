package kosbrother.com.doctorguide.fragments;

import android.app.ProgressDialog;
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

import kosbrother.com.doctorguide.DivisionActivity;
import kosbrother.com.doctorguide.DoctorActivity;
import kosbrother.com.doctorguide.HospitalActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.CommentAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;


public class CommentFragment extends Fragment {

    private static final String ARG_HOSPITAL = "ARG_HOSPITAL";
    private static final String ARG_DIVISION = "ARG_DIVISION";
    private static final String ARG_DOCTOR = "ARG_DOCTOR";
    private static final String ARG_GA_CATEGORY = "ARG_GA_CATEGORY";

    private Integer mHospitalId;
    private Integer mDivisionId;
    private Integer mDoctorId;
    private String mGACategory;

    private ArrayList<Comment> comments;
    private View view;


    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(Integer hospital_id, Integer division_id, Integer doctor_id, String gACategory) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        if (hospital_id != null)
            args.putInt(ARG_HOSPITAL, hospital_id);
        if (division_id != null)
            args.putInt(ARG_DIVISION, division_id);
        if (doctor_id != null)
            args.putInt(ARG_DOCTOR, doctor_id);
        args.putString(ARG_GA_CATEGORY, gACategory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHospitalId = getArguments().getInt(ARG_HOSPITAL);
            mDivisionId = getArguments().getInt(ARG_DIVISION);
            mDoctorId = getArguments().getInt(ARG_DOCTOR);
            mGACategory = getArguments().getString(ARG_GA_CATEGORY);
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

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(getContext());
        }

        @Override
        protected Object doInBackground(Object... params) {
            if (mDivisionId != 0 && mHospitalId != 0)
                comments = DoctorGuideApi.getDivisionComments(mDivisionId, mHospitalId);
            else if (mHospitalId != 0)
                comments = DoctorGuideApi.getHospitalComments(mHospitalId);
            else if (mDoctorId != 0)
                comments = DoctorGuideApi.getDoctorComments(mDoctorId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();
            if (comments.size() == 0) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                View noCommentView = inflater.inflate(R.layout.fragment_no_comment, null);
                noCommentView.setLayoutParams(lparams);
                noCommentView.findViewById(R.id.add_comment_linear_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAddCommentClick();
                    }
                });
                ((RelativeLayout) view.findViewById(R.id.baseLayout)).addView(noCommentView);
            } else {
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                Context context = view.getContext();
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(new CommentAdapter(comments, getContext(), mGACategory));
            }
        }

    }

    private void onAddCommentClick() {
        switch (mGACategory) {
            case GACategory.DOCTOR:
                ((DoctorActivity) getActivity()).onAddCommentClick();
                break;
            case GACategory.DIVISION:
                ((DivisionActivity) getActivity()).onAddCommentClick();
                break;
            case GACategory.HOSPITAL:
                ((HospitalActivity) getActivity()).onAddCommentClick();
                break;
        }
    }

}
