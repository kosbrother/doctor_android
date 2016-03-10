package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.task.GetMyCommentsTask;

public class MyCommentModel {
    private final String userEmail;

    public MyCommentModel(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean isUserSignIn() {
        return userEmail != null;
    }

    public void requestGetMyComments(GetMyCommentsTask.GetMyCommentsListener listener) {
        new GetMyCommentsTask(listener).execute(userEmail);
    }

    public void requestCreateUser(CreateUserTask.CreateUserListener listener) {
        new CreateUserTask(listener).execute(GoogleSignInManager.getInstance().getUser());
    }
}
