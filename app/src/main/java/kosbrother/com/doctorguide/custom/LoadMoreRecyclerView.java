package kosbrother.com.doctorguide.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by steven on 1/10/16.
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private OnLoadMoreListener mOnLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = false;
    private boolean allLoad = false;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    private void init() {

        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager
                        .findLastVisibleItemPosition();
                if (!allLoad  && !loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached
                    // Do something
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    loading = true;
                }
            }
        });
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoaded() {
        loading = false;
    }

}
