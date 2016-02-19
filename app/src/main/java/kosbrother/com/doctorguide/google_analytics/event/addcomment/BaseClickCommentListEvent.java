package kosbrother.com.doctorguide.google_analytics.event.addcomment;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.google_analytics.action.GAAction;
import kosbrother.com.doctorguide.google_analytics.event.GAEvent;

public abstract class BaseClickCommentListEvent implements GAEvent {
    private Comment comment;

    public BaseClickCommentListEvent(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getAction() {
        return GAAction.CLICK_COMMENT_LIST;
    }

    @Override
    public String getLabel() {
        return "醫院: " + comment.hospital_name + "\n" +
                "科別: " + comment.division_name + "\n" +
                "評論id: " + comment.id + "\n" +
                "醫師名稱: " + comment.doctor_name;
    }

    @Override
    public long getValue() {
        return 0;
    }
}
