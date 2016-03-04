package kosbrother.com.doctorguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import kosbrother.com.doctorguide.presenter.BasePresenter;
import kosbrother.com.doctorguide.view.BaseView;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private BasePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BasePresenter(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            presenter.onHomeItemSelected();
        }
        return true;
    }
}
