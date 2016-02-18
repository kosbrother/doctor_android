package kosbrother.com.doctorguide.google_analytics.event.hospital;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.BaseClickCommentListEvent;

public class HospitalClickCommentListEvent extends BaseClickCommentListEvent {

    public HospitalClickCommentListEvent(Comment comment) {
        super(comment);
    }

    @Override
    public String getCategory() {
        return GACategory.HOSPITAL;
    }
}
