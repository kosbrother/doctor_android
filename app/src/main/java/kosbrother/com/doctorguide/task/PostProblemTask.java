package kosbrother.com.doctorguide.task;

import java.util.HashMap;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class PostProblemTask extends AsyncTask<HashMap<String, String>, Void, Boolean> {

    private PostProblemListener listener;

    public PostProblemTask(PostProblemListener listener) {
        this.listener = listener;
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(HashMap<String, String>... hashMaps) {
        return DoctorGuideApi.postProblem(hashMaps[0]);
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess) {
            listener.onPostProblemSuccess();
        }
    }

    public interface PostProblemListener {
        void onPostProblemSuccess();
    }
}
