package kosbrother.com.doctorguide.view;

import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public interface BaseFabView {
    void initFab();

    void setFabImageDrawable(int fabDrawableId);

    void closeFab();

    void sendClickFabEvent(String label);

    void startProblemReportActivity(DivisionAndHospitalViewModel viewModel);

    void startShareActivity();
}
