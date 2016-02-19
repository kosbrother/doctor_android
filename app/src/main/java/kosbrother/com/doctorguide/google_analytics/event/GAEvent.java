package kosbrother.com.doctorguide.google_analytics.event;

public interface GAEvent {
    String getCategory();

    String getAction();

    String getLabel();

    long getValue();
}
