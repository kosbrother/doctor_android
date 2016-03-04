package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.AboutUsView;

public class AboutUsPresenter {
    private AboutUsView view;

    public AboutUsPresenter(AboutUsView view) {
        this.view = view;
    }

    public void onCreate() {
        view.setContentView();
        view.initContentView();
    }

    public void onFeedbackButtonClick() {
        view.startFeedbackActivity();
    }

}
