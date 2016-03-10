package kosbrother.com.doctorguide.task;

import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;
import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.Comment;

public class GetMyCommentsTask extends AsyncTask<String, Void, ArrayList<Comment>> {

    private GetMyCommentsListener listener;

    public GetMyCommentsTask(GetMyCommentsListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Comment> doInBackground(String... strings) {
        return DoctorGuideApi.getUserComments(strings[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Comment> comments) {
        super.onPostExecute(comments);
        if (comments != null) {
            listener.onGetMyCommentsSuccess(comments);
        }
    }

    public interface GetMyCommentsListener {
        void onGetMyCommentsSuccess(ArrayList<Comment> comments);
    }
}
