package kosbrother.com.doctorguide.google_signin;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public interface GoogleSigninInteractor {
    void signOut(ResultCallback<? super Status> resultCallback);
}
