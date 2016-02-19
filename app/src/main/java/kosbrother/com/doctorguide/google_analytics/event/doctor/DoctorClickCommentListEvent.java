package kosbrother.com.doctorguide.google_analytics.event.doctor;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.category.GACategory;
import kosbrother.com.doctorguide.google_analytics.event.addcomment.BaseClickCommentListEvent;

public class DoctorClickCommentListEvent extends BaseClickCommentListEvent {

    public DoctorClickCommentListEvent(Comment comment) {
        super(comment);
    }

    @Override
    public String getCategory() {
        return GACategory.DOCTOR;
    }
}
