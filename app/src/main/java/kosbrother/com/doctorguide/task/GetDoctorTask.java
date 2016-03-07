package kosbrother.com.doctorguide.task;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Doctor;

public class GetDoctorTask extends AsyncTask<Integer, Void, Doctor> {
    private final GetDoctorListener listener;

    public GetDoctorTask(GetDoctorListener listener) {
        this.listener = listener;
    }

    @Override
    protected Doctor doInBackground(Integer... integers) {
        return DoctorGuideApi.getDoctorScore(integers[0]);
    }

    @Override
    protected void onPostExecute(Doctor doctor) {
        super.onPostExecute(doctor);
        if (doctor != null) {
            listener.onGetDoctorSuccess(doctor);
        }
    }

    public interface GetDoctorListener {

        void onGetDoctorSuccess(Doctor doctor);
    }
}
