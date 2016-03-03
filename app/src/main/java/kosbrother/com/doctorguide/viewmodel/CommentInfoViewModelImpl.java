package kosbrother.com.doctorguide.viewmodel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kosbrother.com.doctorguide.entity.Comment;

public class CommentInfoViewModelImpl implements CommentInfoViewModel {
    private final Comment comment;

    public CommentInfoViewModelImpl(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String getCommenter() {
        return comment.user_name;
    }

    @Override
    public String getCommentTime() {
        return new SimpleDateFormat("yyyy/MM/dd", Locale.TAIWAN).format(comment.updated_at);
    }

    @Override
    public String getHospital() {
        return comment.hospital_name;
    }

    @Override
    public String getDivision() {
        return comment.division_name;
    }

    @Override
    public String getDoctor() {
        return comment.doctor_name;
    }
}
