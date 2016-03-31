package kosbrother.com.doctorguide.task;

import android.os.AsyncTask;

import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.User;

public class CreateUserTask extends AsyncTask<User, Void, Integer> {

    private CreateUserListener listener;

    public CreateUserTask(CreateUserListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(User... params) {
        return DoctorGuideApi.createUser(params[0]);
    }

    @Override
    protected void onPostExecute(Integer statusCode) {
        super.onPostExecute(statusCode);
        if (statusCode == 200) {
            listener.onCreateUserSuccess();
        } else {
            listener.onCreateUserFail();
        }
    }

    public interface CreateUserListener {
        void onCreateUserSuccess();

        void onCreateUserFail();
    }
}
