package kosbrother.com.doctorguide.google_analytics.event.addcomment;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class AddCommentSubmitCommentEvent implements GAEvent {

    @Override
    public String getCategory() {
        return GACategory.ADD_COMMENT;
    }

    @Override
    public String getAction() {
        return GAAction.SUBMIT_COMMENT;
    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public long getValue() {
        return 0;
    }
}
