package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;

public interface HospitalFabView extends BaseFabView{
    void startProblemReportActivity(HospitalActivityViewModel viewModel);

    void startAddDoctorActivity(HospitalActivityViewModel viewModel);
}
