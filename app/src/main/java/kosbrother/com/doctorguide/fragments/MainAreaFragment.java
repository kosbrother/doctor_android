package kosbrother.com.doctorguide.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kosbrother.com.doctorguide.AreaActivity;
import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.adapters.AreaAdapter;
import kosbrother.com.doctorguide.entity.Area;
import kosbrother.com.doctorguide.google_analytics.GAManager;
import kosbrother.com.doctorguide.google_analytics.event.main.MainClickAreaListEvent;

public class MainAreaFragment extends Fragment implements AreaAdapter.RecyclerViewClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView) inflater.inflate(R.layout.fragment_main_area, container, false);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.setAdapter(new AreaAdapter(Area.getAreaStrings().toArray(new String[Area.getAreas().size()]), this));
        return view;
    }

    @Override
    public void onItemClick(int position) {
        GAManager.sendEvent(new MainClickAreaListEvent(Area.getAreaStrings().get(position)));
        Intent intent = new Intent(getActivity(), AreaActivity.class);
        intent.putExtra(ExtraKey.INT_AREA_POSITION, position);
        startActivity(intent);
    }
}
