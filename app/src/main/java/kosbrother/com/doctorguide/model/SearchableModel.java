package kosbrother.com.doctorguide.model;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Doctor;
import kosbrother.com.doctorguide.entity.Hospital;
import kosbrother.com.doctorguide.task.SearchDoctorsTask;
import kosbrother.com.doctorguide.task.SearchHospitalsTask;

public class SearchableModel implements
        SearchDoctorsTask.SearchDoctorsListener,
        SearchHospitalsTask.SearchHospitalsListener {

    private String queryString;
    private ArrayList<Doctor> doctors;
    private ArrayList<Hospital> hospitals;

    private GetSearchResultListener getSearchResultListener;
    private int taskCount = 0;

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getActionBarTitle() {
        return "搜尋：" + queryString;
    }

    public void requestGetSearchResult(GetSearchResultListener listener) {
        getSearchResultListener = listener;
        new SearchDoctorsTask(5, this).execute(queryString);
        new SearchHospitalsTask(5, this).execute(queryString);
    }

    @Override
    public void onSearchDoctorsResult(ArrayList<Doctor> doctors) {
        this.doctors = doctors;
        taskCount++;
        countIsAllTasksDone();
    }

    @Override
    public void onSearchHospitalsResult(ArrayList<Hospital> hospitals) {
        this.hospitals = hospitals;
        taskCount++;
        countIsAllTasksDone();
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Hospital> getHospitals() {
        return hospitals;
    }

    private synchronized void countIsAllTasksDone() {
        if (taskCount == 2) {
            getSearchResultListener.onGetSearchResultDone();
            taskCount = 0;
        }
    }

    public interface GetSearchResultListener {
        void onGetSearchResultDone();
    }
}
