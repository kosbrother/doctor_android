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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.adapters.MyDoctorRecyclerViewAdapter;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Area;
import kosbrother.com.doctorguide.entity.Doctor;


public class DoctorFragment extends Fragment implements Spinner.OnItemSelectedListener{

    private static final String ARG_TYPE = "ARG_TYPE";
    private static final String ARG_CATEGORY_ID = "CATEGORY_ID";
    private int fragmentType;
    private int mCategoryId = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Doctor> doctors;
    private RecyclerView recyclerView;


    public DoctorFragment() {
    }

    public static DoctorFragment newInstance(int fragmentType,int categoryId) {
        DoctorFragment fragment = new DoctorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, fragmentType);
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            fragmentType = getArguments().getInt(ARG_TYPE);
            mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doctor_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        Context context = view.getContext();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        if(fragmentType == MyDoctorRecyclerViewAdapter.HEARTTYPE){
            view.findViewById(R.id.selector).setVisibility(View.GONE);
        }

        setAreaSpinner(view);
        setSortSpinner(view);

        return view;
    }

    private void setSortSpinner(View view) {

        Spinner sort = (Spinner) view.findViewById(R.id.sort);
        ArrayAdapter<CharSequence> sort_adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_options, android.R.layout.simple_spinner_item);
        sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(sort_adapter);
        sort.setOnItemSelectedListener(this);
    }

    private void setAreaSpinner(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.area);
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, Area.getAreaStrings().toArray(new String[Area.getAreas().size()]));
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(areaAdapter);
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
        if(spinner.getId() == R.id.area){
            areaId = Area.getAreas().get(position).id;
        }else if(spinner.getId() == R.id.sort){
            sortSrting = (String)parent.getItemAtPosition(position);
        }

        if(areaId != 0 && sortSrting!=null)
            checktime += 1;
        if(checktime >= 1) {
            new GetDoctorsTask().execute();
        }
    }

    private class GetDoctorsTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Util.showProgressDialog(getContext());
        }
        @Override
        protected Object doInBackground(Object... params) {
            doctors = DoctorGuideApi.getDoctorsByAreaAndCategory(areaId, mCategoryId);
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            Util.hideProgressDialog();
            MyDoctorRecyclerViewAdapter adatper = new MyDoctorRecyclerViewAdapter(doctors, mListener, fragmentType);
            recyclerView.setAdapter(adatper);
            adatper.notifyDataSetChanged();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Doctor item);
    }
}
