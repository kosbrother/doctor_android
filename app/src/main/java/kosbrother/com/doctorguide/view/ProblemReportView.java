package kosbrother.com.doctorguide.view;

public interface ProblemReportView extends ProgressDialogView {
    void setContentView();

    void setActionBar();

    void setReportType(String reportTypeText);

    void setReportPage(String reportPageText);

    void showNoContentSnackBar();

    void showSubmitSuccessDialog();
}
