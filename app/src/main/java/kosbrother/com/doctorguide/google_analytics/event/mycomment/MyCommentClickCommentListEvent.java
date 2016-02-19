package kosbrother.com.doctorguide.google_analytics.event.mycomment;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.BaseClickCommentListEvent;

public class MyCommentClickCommentListEvent extends BaseClickCommentListEvent {
    public MyCommentClickCommentListEvent(Comment comment) {
        super(comment);
    }

    @Override
    public String getCategory() {
        return GACategory.MY_COMMENT;
    }
}
