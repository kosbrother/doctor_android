package kosbrother.com.doctorguide.google_analytics.event.mycomment;

import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public class MyCommentClickGoogleSignInEvent implements GAEvent {
    @Override
    public String getCategory() {
        return GACategory.MY_COMMENT;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_GOOGLE_SIGN_IN;
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
