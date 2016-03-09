package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.task.PostFeedbackTask;

public class FeedbackModel {
    public void requestPostFeedback(String title, String content, PostFeedbackTask.PostFeedbackListener listener) {
        new PostFeedbackTask(title, content, listener).execute();
    }
}
