package kosbrother.com.doctorguide.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Hospital;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalDetailFragment extends Fragment {

    private static final String ARG_HOSPITAL_ID = "ARG_HOSPITAL_ID";
    private static Hospital mHospital;
    private int mHospitalId;
    private TextView addressView;
    private TextView phoneView;
    private TextView openTimeView;
    private TextView gradeView;
    private TextView assessView;
    private TextView servicesView;

    public HospitalDetailFragment() {
    }

    public static HospitalDetailFragment newInstance(int hospitalId, Hospital hospital) {
        HospitalDetailFragment fragment = new HospitalDetailFragment();
        Bundle args = new Bundle();
        mHospital = hospital;
        args.putInt(ARG_HOSPITAL_ID, hospitalId);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mHospitalId = getArguments().getInt(ARG_HOSPITAL_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_detail, container, false);
        setViews(view);
        return view;
    }

    public void setViews(View view) {
        addressView = (TextView)view.findViewById(R.id.address);
        phoneView = (TextView)view.findViewById(R.id.phone);
        openTimeView = (TextView)view.findViewById(R.id.open_time);
        gradeView = (TextView)view.findViewById(R.id.grade);
        assessView = (TextView)view.findViewById(R.id.assess);
        servicesView = (TextView) view.findViewById(R.id.services);

        if(mHospital != null)
            setEachView();

    }

    private void setEachView() {
        addressView.setText(mHospital.address);
        phoneView.setText(mHospital.phone);
        openTimeView.setText(hourString());
        gradeView.setText(mHospital.grade);
        assessView.setText(mHospital.assess);
        servicesView.setText(mHospital.ss.toString());
    }

    private String hourString() {
        String hour = "";
        if(mHospital.cHours.containsKey("weekday"))
            hour  = "平日看診  " + mHospital.cHours.get("weekday") + "\n";
        if(mHospital.cHours.containsKey("night"))
            hour += "夜間看診  " + mHospital.cHours.get("night") + "\n";
        if(mHospital.cHours.containsKey("holiday"))
            hour += "假日看診  " + mHospital.cHours.get("holiday") + "\n";
        if(mHospital.cHours.containsKey("special"))
            hour += mHospital.cHours.get("special");
        if(hour.equals(""))
            hour = "目前無門診時間資料";
        return hour;
    }
}
