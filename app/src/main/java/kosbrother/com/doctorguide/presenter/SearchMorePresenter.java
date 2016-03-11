package kosbrother.com.doctorguide.presenter;

import kosbrother.com.doctorguide.view.SearchMoreView;

public class SearchMorePresenter {
    private final SearchMoreView view;

    public SearchMorePresenter(SearchMoreView view) {
        this.view = view;
    }

    public void onCreate() {
        view.setContentView();
        view.setActionBar();
    }
}
