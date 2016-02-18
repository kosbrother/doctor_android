package kosbrother.com.doctorguide.google_analytics.event.addcomment;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class AddCommentSlideEvent implements GAEvent {
    private String label;
    private long value;

    public AddCommentSlideEvent(String label, long value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public String getCategory() {
        return GACategory.ADD_COMMENT;
    }

    @Override
    public String getAction() {
        return GAAction.SLIDE;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public long getValue() {
        return value;
    }
}
