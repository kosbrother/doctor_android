package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public class FabModel {
    private final DivisionAndHospitalViewModel viewModel;
    private String email;

    public FabModel(DivisionAndHospitalViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public int getFabDrawableId(boolean opened) {
        return opened ? R.mipmap.ic_close : R.mipmap.ic_fab;
    }

    public DivisionAndHospitalViewModel getViewModel() {
        return viewModel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void requestCreateUser(User user, CreateUserTask.CreateUserListener listener) {
        new CreateUserTask(listener).execute(user);
    }
}
