package kosbrother.com.doctorguide.viewmodel;

import kosbrother.com.doctorguide.entity.Comment;
import kosbrother.com.doctorguide.viewmodel.DivisionCommentViewModel;

public class DivisionCommentViewModelImpl implements DivisionCommentViewModel {
    private final Comment comment;

    public DivisionCommentViewModelImpl(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getEnvScoreText() {
        return String.format("%d", comment.div_environment);
    }

    @Override
    public int getEnvScore() {
        return comment.div_environment;
    }

    @Override
    public String getEquipmentScoreText() {
        return String.format("%d", comment.div_equipment);
    }

    @Override
    public int getEquipmentScore() {
        return comment.div_equipment;
    }

    @Override
    public String getSpeScoreText() {
        return String.format("%d", comment.div_speciality);
    }

    @Override
    public int getSpeScore() {
        return comment.div_speciality;
    }

    @Override
    public String getFriendScoreText() {
        return String.format("%d", comment.div_friendly);
    }

    @Override
    public int getFriendScore() {
        return comment.div_friendly;
    }

    @Override
    public String getCommentText() {
        String div_comment = comment.div_comment;
        return ((div_comment == null) || div_comment.equals("")) ? "暫無評論" : div_comment;
    }
}
