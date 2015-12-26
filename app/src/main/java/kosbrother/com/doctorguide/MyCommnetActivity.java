package kosbrother.com.doctorguide;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import kosbrother.com.doctorguide.adapters.CommentAdapter;

public class MyCommnetActivity extends AppCompatActivity {

    private ActionBar actionbar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commnet);

        actionbar = getSupportActionBar();
        actionbar.setTitle("我的評論");
        actionbar.setDisplayHomeAsUpEnabled(true);

        setRecyclerView();
    }

    private void setRecyclerView() {
        String[] myStringArray = {"家醫科","家醫科","內科","家醫科","家醫科","內科","家醫科","家醫科","內科","家醫科","家醫科","內科"};
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CommentAdapter(this,myStringArray));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
