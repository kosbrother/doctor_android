package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.entity.User;
import kosbrother.com.doctorguide.task.CreateUserTask;
import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public class FabModel extends BaseFabModel {
    private String email;

    public FabModel(DivisionAndHospitalViewModel viewModel) {
        super(viewModel);
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
