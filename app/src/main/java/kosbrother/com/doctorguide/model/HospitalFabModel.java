package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.viewmodel.HospitalActivityViewModel;

public class HospitalFabModel extends BaseFabModel{
    private final HospitalActivityViewModel viewModel;

    public HospitalFabModel(HospitalActivityViewModel viewModel) {
        super();
        this.viewModel = viewModel;
    }

    public HospitalActivityViewModel getViewModel() {
        return viewModel;
    }
}
