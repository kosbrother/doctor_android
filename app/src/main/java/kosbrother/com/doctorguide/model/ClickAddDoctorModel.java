package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.viewmodel.AddDoctorViewModel;
import kosbrother.com.doctorguide.viewmodel.DivisionActivityViewModel;

public class ClickAddDoctorModel {

    private String divisionName = "";
    private String hospitalName = "";
    private int divisionId = 0;
    private int hospitalId = 0;

    public ClickAddDoctorModel(DivisionActivityViewModel viewModel) {
        divisionName = viewModel.getDivisionName();
        hospitalName = viewModel.getHospitalName();
        divisionId = viewModel.getDivisionId();
        hospitalId = viewModel.getHospitalId();
    }

    public AddDoctorViewModel getAddDoctorViewModel() {
        return new AddDoctorViewModel() {
            @Override
            public String getDivisionName() {
                return divisionName;
            }

            @Override
            public String getHospitalName() {
                return hospitalName;
            }

            @Override
            public int getDivisionId() {
                return divisionId;
            }

            @Override
            public int getHospitalId() {
                return hospitalId;
            }
        };
    }
}
