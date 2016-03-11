package kosbrother.com.doctorguide.task;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Doctor;

public class SearchDoctorsTask extends AsyncTask<String, Void, ArrayList<Doctor>> {
    private int searchNum;
    private SearchDoctorsListener listener;

    public SearchDoctorsTask(int searchNum, SearchDoctorsListener listener) {
        this.searchNum = searchNum;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Doctor> doInBackground(String... strings) {
        return DoctorGuideApi.searchDoctors(strings[0], searchNum);
    }

    @Override
    protected void onPostExecute(ArrayList<Doctor> doctors) {
        super.onPostExecute(doctors);
        listener.onSearchDoctorsResult(doctors);
    }

    public interface SearchDoctorsListener {
        void onSearchDoctorsResult(ArrayList<Doctor> doctors);
    }
}
