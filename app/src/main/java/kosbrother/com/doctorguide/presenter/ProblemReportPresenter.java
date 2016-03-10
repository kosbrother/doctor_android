package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.model.ProblemReportModel;
import kosbrother.com.doctorguide.task.PostProblemTask;
import kosbrother.com.doctorguide.view.ProblemReportView;

public class ProblemReportPresenter implements PostProblemTask.PostProblemListener {
    private final ProblemReportView view;
    private final ProblemReportModel model;

    public ProblemReportPresenter(ProblemReportView view, ProblemReportModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();
        view.setReportType(model.getReportTypeText());
        view.setReportPage(model.getReportPageText());
    }

    public void onSubmitClick(String content) {
        if (content.isEmpty()) {
            view.showNoContentSnackBar();
        } else {
            view.showProgressDialog();
            model.requestPostProblem(content, this);
        }
    }

    @Override
    public void onPostProblemSuccess() {
        view.hideProgressDialog();
        view.showSubmitSuccessDialog();
    }
}
