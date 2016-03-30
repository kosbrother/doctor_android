package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.google_signin.SignInManager;
import kosbrother.com.doctorguide.viewmodel.AddCommentViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.DoctorActivityViewModel;
import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;

public class ClickAddCommentModel {

    private String hospitalName = "";
    private int hospitalId = 0;
    private int doctorId = 0;
    private int divisionId = 0;
    private String user = "";

    public ClickAddCommentModel(DivisionActivityViewModel viewModel) {
        hospitalName = viewModel.getHospitalName();
        hospitalId = viewModel.getHospitalId();
        divisionId = viewModel.getDivisionId();
        doctorId = viewModel.getDivisionId();
        user = SignInManager.getInstance().getEmail();
    }

    public ClickAddCommentModel(DoctorActivityViewModel viewModel) {
        hospitalName = viewModel.getHospitalName();
        hospitalId = viewModel.getHospitalId();
        doctorId = viewModel.getDoctorId();
        user = SignInManager.getInstance().getEmail();
    }

    public ClickAddCommentModel(HospitalActivityViewModel viewModel) {
        hospitalName = viewModel.getHospitalName();
        hospitalId = viewModel.getHospitalId();
        user = SignInManager.getInstance().getEmail();
    }

    public boolean isSignIn() {
        return SignInManager.getInstance().isSignIn();
    }

    public AddCommentViewModel getAddCommentViewModel() {
        return new AddCommentViewModel() {
            @Override
            public String getHospitalName() {
                return hospitalName;
            }

            @Override
            public int getHospitalId() {
                return hospitalId;
            }

            @Override
            public int getDoctorId() {
                return doctorId;
            }

            @Override
            public int getDivisionId() {
                return divisionId;
            }

            @Override
            public String getUser() {
                return user;
            }
        };
    }
}
