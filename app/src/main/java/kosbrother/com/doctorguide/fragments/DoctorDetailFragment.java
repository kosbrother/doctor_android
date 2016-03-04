package kosbrother.com.doctorguide.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kosbrother.com.doctorguide.DivisionActivity;
import kosbrother.com.doctorguide.HospitalActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.StringUtil;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;
import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickDivisionTextEvent;
import kosbrother.com.doctorguide.google_analytics.event.doctor.DoctorClickHospitalTextEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDetailFragment extends Fragment {

    private static final String ARG_DOCTOR_ID = "ARG_DOCTOR_ID";
    private int mDoctorId;
    private Doctor doctor;
    private TextView exp;
    private TextView spe;
    private LinearLayout hospitalsLayout;

    public DoctorDetailFragment() {

    }

    public static DoctorDetailFragment newInstance(int doctorId) {
        DoctorDetailFragment fragment = new DoctorDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_DOCTOR_ID, doctorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mDoctorId = getArguments().getInt(ARG_DOCTOR_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor_detail, container, false);
        setViews(view);
        return view;
    }

    private void setViews(View view) {
        exp = (TextView) view.findViewById(R.id.exp);
        spe = (TextView) view.findViewById(R.id.spe);
        hospitalsLayout = (LinearLayout) view.findViewById(R.id.hospitals);
        new SetDoctorTask().execute();
    }

    private class SetDoctorTask extends AsyncTask {

        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = Util.showProgressDialog(getContext());
        }

        @Override
        protected Object doInBackground(Object... params) {
            doctor = DoctorGuideApi.getDoctorInfo(mDoctorId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            ArrayList<Integer> hospitalIds = new ArrayList<>();

            mProgressDialog.dismiss();
            exp.setText(doctor.exp);
            spe.setText(doctor.spe);
            for (final Division div : doctor.divisions) {
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lparams.setMargins(0, 0, 0, 4);

                if (!hospitalIds.contains(div.hospital_id)) {
                    hospitalIds.add(div.hospital_id);
                    TextView tv = new TextView(getContext());
                    tv.setTextSize(20);
                    tv.setTextColor(ContextCompat.getColor(getContext(), R.color.orange_text_link));
                    tv.setClickable(true);
                    tv.setLayoutParams(lparams);
                    String htmlString = StringUtil.appendHtmlUnderline(div.hospital_name);
                    tv.setText(Html.fromHtml(htmlString));
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GAManager.sendEvent(new DoctorClickHospitalTextEvent(div.hospital_name));

                            Intent intent = new Intent(getContext(), HospitalActivity.class);
                            intent.putExtra(ExtraKey.HOSPITAL_ID, div.hospital_id);
                            intent.putExtra(ExtraKey.HOSPITAL_GRADE, div.hospital_grade);
                            intent.putExtra(ExtraKey.HOSPITAL_NAME, div.hospital_name);
                            startActivity(intent);
                        }
                    });
                    hospitalsLayout.addView(tv);
                }

                TextView tv2 = new TextView(getContext());
                tv2.setTextSize(20);
                tv2.setTextColor(ContextCompat.getColor(getContext(), R.color.orange_text_link));
                tv2.setClickable(true);
                tv2.setLayoutParams(lparams);
                String htmlString2 = StringUtil.appendHtmlUnderline(div.name);
                tv2.setText(Html.fromHtml(htmlString2));
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GAManager.sendEvent(new DoctorClickDivisionTextEvent(div.name));

                        Intent intent = new Intent(getContext(), DivisionActivity.class);
                        intent.putExtra(ExtraKey.DIVISION_ID, div.id);
                        intent.putExtra(ExtraKey.DIVISION_NAME, div.name);
                        intent.putExtra(ExtraKey.HOSPITAL_ID, div.hospital_id);
                        intent.putExtra(ExtraKey.HOSPITAL_GRADE, div.hospital_grade);
                        intent.putExtra(ExtraKey.HOSPITAL_NAME, div.hospital_name);
                        startActivity(intent);
                    }
                });
                hospitalsLayout.addView(tv2);
            }
        }

    }

}
