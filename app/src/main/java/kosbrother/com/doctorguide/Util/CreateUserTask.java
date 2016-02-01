package kosbrother.com.doctorguide.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import kosbrother.com.doctorguide.api.DoctorGuideApi;
import kosbrother.com.doctorguide.entity.User;

/**
 * Created by steven on 2/1/16.
 */
public class CreateUserTask extends AsyncTask {

    private final Context mContext;
    private final User mUser;
    private ProgressDialog mProgressDialog;
    private int statusCode;

    public CreateUserTask(Context context,User user){
        mContext = context;
        mUser = user;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = Util.showProgressDialog(mContext);
    }
    @Override
    protected Object doInBackground(Object... params) {
        statusCode = DoctorGuideApi.creatUser(mUser);
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        mProgressDialog.dismiss();
        if(statusCode == 200){
            ((AfterCreateUser)mContext).afterCreateUser();
        }else{
            Toast.makeText(mContext, "登入失敗", Toast.LENGTH_SHORT).show();
        }
    }

    public interface AfterCreateUser{
        void afterCreateUser();
    }

}
