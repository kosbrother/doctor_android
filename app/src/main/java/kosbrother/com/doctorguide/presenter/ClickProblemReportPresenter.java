package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.ClickProblemReportModel;
import kosbrother.com.doctorguide.view.ClickProblemReportView;

public class ClickProblemReportPresenter {
    private final ClickProblemReportView view;
    private final ClickProblemReportModel model;

    public ClickProblemReportPresenter(ClickProblemReportView view, ClickProblemReportModel model) {
        this.view = view;
        this.model = model;
    }

    public void onProblemReportClick() {
        view.startProblemReportActivity(model.getViewModel());
    }
}
