package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.viewmodel.DoctorFabViewModel;
import kosbrother.com.doctorguide.google_signin.GoogleSignInManager;
import kosbrother.com.doctorguide.task.CreateUserTask;

public class DoctorFabModel extends BaseFabModel {

    private DoctorFabViewModel viewModel;

    public DoctorFabModel(DoctorFabViewModel viewModel) {
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

    public DoctorFabViewModel getViewModel() {
        return viewModel;
    }
}
