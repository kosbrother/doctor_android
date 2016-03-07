package kosbrother.com.doctorguide.model;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.viewmodel.DivisionAndHospitalViewModel;

public class BaseFabModel {
    private DivisionAndHospitalViewModel viewModel;

    public BaseFabModel(DivisionAndHospitalViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public DivisionAndHospitalViewModel getViewModel() {
        return viewModel;
    }

    public int getFabDrawableId(boolean opened) {
        return opened ? R.mipmap.ic_close : R.mipmap.ic_fab;
    }
}
