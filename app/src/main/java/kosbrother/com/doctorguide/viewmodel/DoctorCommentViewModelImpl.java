package kosbrother.com.doctorguide.viewmodel;

import kosbrother.com.doctorguide.entity.Comment;

public class DoctorCommentViewModelImpl implements DoctorCommentViewModel {
    private final Comment comment;

    public DoctorCommentViewModelImpl(Comment comment) {
        this.comment = comment;
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
    public String getSpecialtyText() {
        return String.format("%d", comment.dr_speciality);
    }

    @Override
    public int getSpecialty() {
        return comment.dr_speciality;
    }

    @Override
    public boolean isRecommend() {
        return comment.is_recommend;
    }

    @Override
    public String getComment() {
        String dr_comment = comment.dr_comment;
        return ((dr_comment == null) || dr_comment.equals("")) ? "暫無評論" : dr_comment;
    }
}
