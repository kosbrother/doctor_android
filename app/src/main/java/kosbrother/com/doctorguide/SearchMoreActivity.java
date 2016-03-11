package kosbrother.com.doctorguide;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import kosbrother.com.doctorguide.Util.ExtraKey;
import kosbrother.com.doctorguide.Util.Util;
import kosbrother.com.doctorguide.view.SearchMoreView;

public class SearchMoreActivity extends BaseActivity implements SearchMoreView {

    private ProgressDialog progressDialog;

    protected String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtras();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = Util.showProgressDialog(this);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search_more);
    }

    @Override
    public void setActionBar() {
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setTitle("搜尋更多");
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void getExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                query = extras.getString(ExtraKey.QUERY);
            }
        }
    }
}
