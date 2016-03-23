package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.ClickShareView;

public class ClickSharePresenter {
    private final ClickShareView view;

    public ClickSharePresenter(ClickShareView view) {
        this.view = view;
    }

    public void onShareClick() {
        view.startShareActivity();
    }
}
