package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.google_signin.SignInManager;
import kosbrother.com.doctorguide.task.GetMyCommentsTask;

public class MyCommentModel {

    public boolean isUserSignIn() {
        return SignInManager.getInstance().isSignIn();
    }

    public void requestGetMyComments(GetMyCommentsTask.GetMyCommentsListener listener) {
        new GetMyCommentsTask(listener).execute(SignInManager.getInstance().getEmail());
    }
}
