package kosbrother.com.doctorguide.task;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class PostFeedbackTask extends AsyncTask<Void, Void, Boolean> {
    private String subject;
    private String content;
    private PostFeedbackListener listener;

    public PostFeedbackTask(String subject, String content, PostFeedbackListener listener) {
        this.subject = subject;
        this.content = content;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return DoctorGuideApi.postFeedback(subject, content);
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        if (isSuccess) {
            listener.onPostFeedbackSuccess();
        }
    }

    public interface PostFeedbackListener {
        void onPostFeedbackSuccess();
    }
}
