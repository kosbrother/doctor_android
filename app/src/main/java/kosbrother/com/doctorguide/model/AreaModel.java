package kosbrother.com.doctorguide.model;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Area;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.entity.OrderStrings;
import kosbrother.com.doctorguide.task.GetHospitalsByAreaTask;

import static kosbrother.com.doctorguide.task.GetHospitalsByAreaTask.GetHospitalsByAreaInput;
import static kosbrother.com.doctorguide.task.GetHospitalsByAreaTask.GetHospitalsByAreaListener;

public class AreaModel {

    private int sortPosition = OrderStrings.getStringIndex(OrderStrings.COMMENT_NUM);
    private int areaPosition;
    private int page = 1;
    private boolean isLoadCompleted = false;

    private LatLng latLng;
    private ArrayList<Hospital> hospitals;

    public AreaModel(int areaPosition) {
        this.areaPosition = areaPosition;
    }

    public void requestGetHospitals(GetHospitalsByAreaListener listener) {
        new GetHospitalsByAreaTask(listener).execute(getInput());
    }

    @NonNull
    private GetHospitalsByAreaInput getInput() {
        GetHospitalsByAreaInput input = new GetHospitalsByAreaInput();
        input.setAreaId(Area.getAreas().get(areaPosition).id);
        input.setLatitude(latLng.latitude);
        input.setLongitude(latLng.longitude);
        input.setOrderString(OrderStrings.getOrderString(sortPosition));
        input.setPage(page);
        return input;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getAreaName() {
        return Area.getAreaStrings().get(areaPosition);
    }

    public void setAreaPosition(int areaPosition) {
        this.areaPosition = areaPosition;
    }

    public int getAreaPosition() {
        return areaPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setHospitals(ArrayList<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public ArrayList<Hospital> getHospitals() {
        return hospitals;
    }

    public void addHospitals(ArrayList<Hospital> hospitals) {
        this.hospitals.addAll(hospitals);
    }

    public int getLoadPage() {
        return page;
    }

    public void plusLoadPage() {
        page++;
    }

    public void setLoadCompleted() {
        isLoadCompleted = true;
    }

    public boolean isLoadCompleted() {
        return isLoadCompleted;
    }

    public void resetToFirstLoad() {
        page = 1;
        isLoadCompleted = false;
    }

    public String[] getOrderStringNameArray() {
        return OrderStrings.getOrderStringNameArray();
    }

    public String[] getAreaStringArray() {
        return Area.getAreaStrings().toArray(new String[Area.getAreas().size()]);
    }
}
