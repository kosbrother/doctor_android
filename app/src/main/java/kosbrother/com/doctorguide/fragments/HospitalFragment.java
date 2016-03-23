package kosbrother.com.doctorguide.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.GetLocation;
import kosbrother.com.doctorguide.Util.OrderStrings;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyHospitalRecyclerViewAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.custom.LoadMoreRecyclerView;
import kosbrother.com.doctorguide.entity.Area;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.hospitaldoctor.HospitalDoctorClickAreaSpinnerEvent;
import kosbrother.com.doctorguide.google_analytics.event.hospitaldoctor.HospitalDoctorClickSortSpinnerEvent;

public class HospitalFragment extends Fragment implements Spinner.OnItemSelectedListener {

    private static final String ARG_CATEGORY_ID = "CATEGORY_ID";
    private static LatLng location;
    private int mCategoryId = 1;
    private OnListFragmentInteractionListener mListener;
    private LoadMoreRecyclerView recyclerView;
    private MyHospitalRecyclerViewAdapter hospitalAdapter;
    private int page = 1;
    private LinearLayout loadmoreLayout;
    private boolean isLoadCompleted = false;
    private String orderString;

    public HospitalFragment() {
    }

    public static HospitalFragment newInstance(int category) {
        HospitalFragment fragment = new HospitalFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_list, container, false);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.list);
        loadmoreLayout = (LinearLayout) view.findViewById(R.id.load_more);
        Context context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!isLoadCompleted) {
                    loadmoreLayout.setVisibility(View.VISIBLE);
                    new GetHospitalsTask().execute();
                }
            }
        });
        setAreaSpinner(view);
        setSortSpinner(view);

        return view;
    }

    private void setSortSpinner(View view) {
        Spinner sort = (Spinner) view.findViewById(R.id.sort);
        ArrayAdapter<String> sort_adapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_area_item, OrderStrings.getOrderStringNameArray());
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(sort_adapter);
        int commentNumPosition = OrderStrings.getStringIndex(OrderStrings.COMMENT_NUM);
        sort.setSelection(commentNumPosition);
        sort.setOnItemSelectedListener(this);
    }

    private void setAreaSpinner(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.area);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<>(getContext(),
                R.layout.spinner_area_item, Area.getAreaStrings().toArray(new String[Area.getAreas().size()]));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaAdapter);
        int closest = Util.getClosestAreaPosition(location);
        spinner.setSelection(closest);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        if (context instanceof GetLocation) {
            location = ((GetLocation) context).getLocation();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    int areaId = 0;
    String sortSrting;
    int checktime = 0;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if (spinner.getId() == R.id.area) {
            areaId = Area.getAreas().get(position).id;

            GAManager.sendEvent(new HospitalDoctorClickAreaSpinnerEvent(Area.getAreaStrings().get(position)));
        } else if (spinner.getId() == R.id.sort) {
            sortSrting = (String) parent.getItemAtPosition(position);
            orderString = OrderStrings.getOrderString(position);

            GAManager.sendEvent(new HospitalDoctorClickSortSpinnerEvent(sortSrting));
        }

        if (areaId != 0 && sortSrting != null)
            checktime += 1;
        if (checktime >= 1) {
            page = 1;
            isLoadCompleted = false;
            new GetHospitalsTask().execute();
        }
    }

    private class GetHospitalsTask extends AsyncTask {

        private ArrayList<Hospital> getHospitals;
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (page == 1)
                mProgressDialog = Util.showProgressDialog(getContext());
        }

        @Override
        protected Object doInBackground(Object... params) {
            getHospitals = DoctorGuideApi.getHospitalsByAreaAndCategory(areaId, mCategoryId, page, location.latitude, location.longitude, orderString);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            if (page == 1)
                mProgressDialog.dismiss();
            loadmoreLayout.setVisibility(View.GONE);
            recyclerView.setLoaded();

            if (page == 1) {
                page += 1;
                hospitalAdapter = new MyHospitalRecyclerViewAdapter(getHospitals, mListener, location);
                recyclerView.setAdapter(hospitalAdapter);
            } else {
                if (getHospitals.size() > 0) {
                    page += 1;
                    hospitalAdapter.addHospitals(getHospitals);
                    hospitalAdapter.notifyDataSetChanged();
                } else {
                    isLoadCompleted = true;
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Hospital item);
    }
}
