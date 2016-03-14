package kosbrother.com.doctorguide.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.adapters.AreaAdapter;
import kosbrother.com.doctorguide.entity.Area;

public class MainAreaFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_main_area, container, false);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(new AreaAdapter(Area.getAreaStrings().toArray(new String[Area.getAreas().size()]), this));
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO: 2016/3/14  start to area activity
    }
}
