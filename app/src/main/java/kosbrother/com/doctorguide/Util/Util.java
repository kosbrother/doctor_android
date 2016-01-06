package kosbrother.com.doctorguide.Util;

import android.app.ProgressDialog;
import android.content.Context;

import kosbrother.com.doctorguide.R;

/**
 * Created by steven on 1/6/16.
 */
public class Util {

    private static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.pull_to_refresh_refreshing_label));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
