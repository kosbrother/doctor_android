package kosbrother.com.doctorguide.google_analytics.event.division;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.BaseClickCommentListEvent;

public class DivisionClickCommentListEvent extends BaseClickCommentListEvent {

    public DivisionClickCommentListEvent(Comment comment) {
        super(comment);
    }

    @Override
    public String getCategory() {
        return GACategory.DIVISION;
    }
}
