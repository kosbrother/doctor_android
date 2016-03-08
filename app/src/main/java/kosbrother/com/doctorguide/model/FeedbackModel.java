package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.task.PostCommentTask;

public class FeedbackModel {
    public void requestPostComment(String title, String content, PostCommentTask.PostCommentListener listener) {
        new PostCommentTask(title, content, listener).execute();
    }
}
