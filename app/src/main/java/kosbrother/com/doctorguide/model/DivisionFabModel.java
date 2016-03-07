package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;

public class DivisionFabModel extends BaseFabModel {

    private DivisionActivityViewModel viewModel;

    public DivisionFabModel(DivisionActivityViewModel viewModel) {
        super();
        this.viewModel = viewModel;
    }

    public boolean isSignIn() {
        return GoogleSignInManager.getInstance().isSignIn();
    }

    public String getEmail() {
        return GoogleSignInManager.getInstance().getEmail();
    }

    public void requestCreateUser(CreateUserTask.CreateUserListener listener) {
        new CreateUserTask(listener).execute(GoogleSignInManager.getInstance().getUser());
    }

    public DivisionActivityViewModel getViewModel() {
        return viewModel;
    }
}
