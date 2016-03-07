package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.google_analytics.label.GALabel;
import kosbrother.com.doctorguide.model.BaseFabModel;
import kosbrother.com.doctorguide.view.BaseFabView;

public class BaseFabPresenter {

    private final BaseFabView view;
    private final BaseFabModel model;

    public BaseFabPresenter(BaseFabView view, BaseFabModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreate() {
        view.initFab();
    }

    public void onFabMenuToggle(boolean opened) {
        view.sendClickFabEvent(GALabel.FAB_MENU);
        view.setFabImageDrawable(model.getFabDrawableId(opened));
    }

    public void onFabShareClick() {
        view.closeFab();
        view.sendClickFabEvent(GALabel.SHARE);
        view.startShareActivity();
    }

}
