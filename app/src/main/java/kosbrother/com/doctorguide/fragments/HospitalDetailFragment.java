package kosbrother.com.doctorguide.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Hospital;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalDetailFragment extends Fragment {

    private static final String ARG_HOSPITAL_ID = "ARG_HOSPITAL_ID";
    private static Hospital mHospital;

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
        if (mHospital == null) {
            return;
        }
        setText(view);
        addGoogleMap();
    }

    private void setText(View view) {
        ((TextView) view.findViewById(R.id.address)).setText(mHospital.address);
        ((TextView) view.findViewById(R.id.phone)).setText(mHospital.phone);
        ((TextView) view.findViewById(R.id.open_time)).setText(hourString());
        ((TextView) view.findViewById(R.id.grade)).setText(mHospital.grade);
        ((TextView) view.findViewById(R.id.assess)).setText(mHospital.assess);
        ((TextView) view.findViewById(R.id.services)).setText(mHospital.ss.toString());
    }

    private void addGoogleMap() {
        SupportMapFragment mMapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().add(R.id.map_container, mMapFragment).commit();
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng addressLatLng = getLatLngFromAddress(getContext(), mHospital.address);
                if (addressLatLng != null) {
                    googleMap.addMarker(new MarkerOptions().position(addressLatLng).title(mHospital.name));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(addressLatLng));
                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                }
            }
        });
    }

    public LatLng getLatLngFromAddress(Context context, String strAddress) {
        LatLng latLng = null;
        Geocoder coder = new Geocoder(context);
        List<Address> addressList;

        try {
            addressList = coder.getFromLocationName(strAddress, 1);
            if (addressList == null || addressList.size() == 0) {
                return null;
            }

            Address address = addressList.get(0);
            latLng = new LatLng(address.getLatitude(), address.getLongitude());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }

    private String hourString() {
        String hour = "";
        if (mHospital.cHours.containsKey("weekday"))
            hour = "平日看診  " + mHospital.cHours.get("weekday") + "\n";
        if (mHospital.cHours.containsKey("night"))
            hour += "夜間看診  " + mHospital.cHours.get("night") + "\n";
        if (mHospital.cHours.containsKey("holiday"))
            hour += "假日看診  " + mHospital.cHours.get("holiday") + "\n";
        if (mHospital.cHours.containsKey("special"))
            hour += mHospital.cHours.get("special");
        if (hour.equals(""))
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

    public interface GetHospital {
        Hospital getHospital();
    }
}
