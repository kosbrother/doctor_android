package kosbrother.com.doctorguide.task;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Hospital;

public class SearchHospitalsTask extends AsyncTask<String, Void, ArrayList<Hospital>> {
    private int searchNum;
    private SearchHospitalsListener listener;

    public SearchHospitalsTask(int searchNum, SearchHospitalsListener listener) {
        this.searchNum = searchNum;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Hospital> doInBackground(String... strings) {
        return DoctorGuideApi.searchHospitals(strings[0], searchNum);
    }

    @Override
    protected void onPostExecute(ArrayList<Hospital> hospitals) {
        super.onPostExecute(hospitals);
        listener.onSearchHospitalsResult(hospitals);
    }

    public interface SearchHospitalsListener {
        void onSearchHospitalsResult(ArrayList<Hospital> hospitals);
    }
}
