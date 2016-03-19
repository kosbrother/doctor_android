package kosbrother.com.doctorguide.view;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Hospital;

public interface AreaView extends ProgressDialogView{
    void setContentView();

    void setActionBar();

    void setActionBarTitle(String title);

    void setOrderSpinner(int sortPosition, String[] orderStringArray);

    void setAreaSpinner(int areaPosition, String[] areaStringArray);

    void setRecyclerView(ArrayList<Hospital> hospitals, LatLng latLng);

    void hideLoadMoreLayout();

    void showLoadMoreLayout();

    void setRecyclerViewLoaded();

    void updateRecyclerView(ArrayList<Hospital> hospitals);

    void startHospitalActivity(Hospital hospital);

    void sendAreaClickAreaSpinnerEvent(String areaName);

    void sendAreaClickSortSpinnerEvent(String sortString);

    void sendAreaClickHospitalItemEvent(String hospitalName);
}
