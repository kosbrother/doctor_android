package kosbrother.com.doctorguide.view;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Comment;

public interface MyCommentView extends ProgressDialogView {
    void setContentView();

    void setActionBar();

    void showMyCommentSingInDialog();

    void showNoCommentLayout();

    void setRecyclerView(ArrayList<Comment> comments);

}
