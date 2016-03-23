package kosbrother.com.doctorguide.google_analytics.event.division;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class DivisionClickAddCommentEvent implements GAEvent {
    private final String label;

    public DivisionClickAddCommentEvent(String label) {
        this.label = label;
    }

    @Override
    public String getCategory() {
        return GACategory.DIVISION;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_ADD_COMMENT;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public long getValue() {
        return 0;
    }
}
