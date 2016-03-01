package kosbrother.com.doctorguide.task;

import android.os.AsyncTask;

import java.util.ArrayList;

import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;

public class GetDivisionScoreTask extends AsyncTask<Integer, Void, ArrayList<Division>> {

    private GetDivisionScoreListener listener;

    public GetDivisionScoreTask(GetDivisionScoreListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Division> doInBackground(Integer... params) {
        return DoctorGuideApi.getDivisionsWithDoctorsByHospital(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Division> divisions) {
        super.onPostExecute(divisions);
        listener.onGetDivisionScoreResult(divisions);
    }

    public interface GetDivisionScoreListener {
        void onGetDivisionScoreResult(ArrayList<Division> divisions);
    }
}
