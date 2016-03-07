package kosbrother.com.doctorguide.view;

public interface BaseFabView {
    void initFab();

    void setFabImageDrawable(int fabDrawableId);

    void closeFab();

    void sendClickFabEvent(String label);

    void startShareActivity();
}
