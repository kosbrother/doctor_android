package kosbrother.com.doctorguide.task;

import android.os.AsyncTask;

import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;

public class GetCommentTask extends AsyncTask<Integer, Void, Comment> {

    private GetCommentListener listener;

    public GetCommentTask(GetCommentListener listener) {
        this.listener = listener;
    }

    @Override
    protected Comment doInBackground(Integer... params) {
        return DoctorGuideApi.getComment(params[0]);
    }

    @Override
    protected void onPostExecute(Comment comment) {
        super.onPostExecute(comment);
        if (comment != null) {
            listener.onGetCommentResult(comment);
        }
    }

    public interface GetCommentListener {
        void onGetCommentResult(Comment comment);
    }
}
