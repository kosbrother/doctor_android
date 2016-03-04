package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.BaseView;

public class BasePresenter {
    private final BaseView view;

    public BasePresenter(BaseView view) {
        this.view = view;
    }

    public void onHomeItemSelected() {
        view.finish();
    }
}
