package kosbrother.com.doctorguide.task;

import android.os.AsyncTask;

import java.util.ArrayList;

import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Division;

public class GetDivisionsByHospitalTask extends AsyncTask<Integer, Void, ArrayList<Division>> {

    private GetDivisionsListener listener;

    public GetDivisionsByHospitalTask(GetDivisionsListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Division> doInBackground(Integer... params) {
        return DoctorGuideApi.getDivisionByHospital(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Division> divisions) {
        super.onPostExecute(divisions);
        if (divisions != null && divisions.size() > 0) {
            listener.onGetDivisionsSuccess(divisions);
        }
    }

    public interface GetDivisionsListener {
        void onGetDivisionsSuccess(ArrayList<Division> divisions);
    }
}
