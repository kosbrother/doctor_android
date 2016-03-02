package kosbrother.com.doctorguide.task;


import android.os.AsyncTask;

import java.util.HashMap;

import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class SubmitAddDoctorTask extends AsyncTask<HashMap<String, String>, Void, Boolean> {

    private SubmitAddDoctorListener listener;

    public SubmitAddDoctorTask(SubmitAddDoctorListener listener) {
        this.listener = listener;
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(HashMap<String, String>... params) {
        return DoctorGuideApi.postAddDoctor(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess) {
            listener.onSubmitResultSuccess();
        }
    }

    public interface SubmitAddDoctorListener {
        void onSubmitResultSuccess();
    }
}
