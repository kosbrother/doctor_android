package kosbrother.com.doctorguide.fragments;


import android.content.Context;
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
    private TextView addressView;
    private TextView phoneView;
    private TextView openTimeView;
    private TextView gradeView;
    private TextView assessView;
    private TextView servicesView;

    public HospitalDetailFragment() {
    }

    public static HospitalDetailFragment newInstance() {
        HospitalDetailFragment fragment = new HospitalDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof GetHospital) {
            mHospital = ((GetHospital) context).getHospital();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GetDoctor");
        }
    }

    public interface GetHospital{
        Hospital getHospital();
    }
}
