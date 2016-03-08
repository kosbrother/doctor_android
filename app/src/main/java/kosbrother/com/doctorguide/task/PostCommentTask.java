package kosbrother.com.doctorguide.task;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;

public class PostCommentTask extends AsyncTask<Void, Void, Boolean> {
    private String subject;
    private String content;
    private PostCommentListener listener;

    public PostCommentTask(String subject, String content, PostCommentListener listener) {
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
            listener.onPostCommentSuccess();
        }
    }

    public interface PostCommentListener {
        void onPostCommentSuccess();
    }
}
