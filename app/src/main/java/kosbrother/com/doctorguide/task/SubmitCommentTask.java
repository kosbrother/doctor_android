package kosbrother.com.doctorguide.task;

import android.os.AsyncTask;

import java.util.HashMap;

import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class SubmitCommentTask extends AsyncTask<HashMap<String, String>, Void, Boolean> {

    private SubmitCommentListener listener;

    public SubmitCommentTask(SubmitCommentListener listener) {
        this.listener = listener;
    }

    @SafeVarargs
    @Override
    protected final Boolean doInBackground(HashMap<String, String>... params) {
        return DoctorGuideApi.postComment(params[0]);
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess) {
            listener.onPostCommentResultSuccess();
        }
    }

    public interface SubmitCommentListener {
        void onPostCommentResultSuccess();
    }
}
