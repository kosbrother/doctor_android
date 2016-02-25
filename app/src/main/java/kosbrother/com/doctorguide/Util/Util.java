package kosbrother.com.doctorguide.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Area;

import static kosbrother.com.doctorguide.Util.SphericalUtil.computeDistanceBetween;

/**
 * Created by steven on 1/6/16.
 */
public class Util {

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.pull_to_refresh_refreshing_label));
        mProgressDialog.setIndeterminate(true);

        mProgressDialog.show();
        return mProgressDialog;
    }


    public static String formatNumber(double distance) {
        String unit = "m";
        if (distance < 1) {
            distance *= 1000;
            unit = "mm";
        } else if (distance > 1000) {
            distance /= 1000;
            unit = "km";
        }

        return String.format("%.1f%s", distance, unit);
    }

    public static void showSnackBar(View v, String str) {
        Snackbar snackbar = Snackbar.make(v, str, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.RED);
        snackbar.show();
    }

    public static int getClosestAreaPosition(final LatLng location) {
        ArrayList<Area> areas = Area.getAreas();
        Collections.sort(areas,
                new Comparator<Area>() {
                    public int compare(Area o1, Area o2) {
                        double d1 = computeDistanceBetween(location, new LatLng(o1.latitude, o1.longitude));
                        double d2 = computeDistanceBetween(location, new LatLng(o2.latitude, o2.longitude));
                        if (d1 - d2 > 0)
                            return 1;
                        else
                            return -1;
                    }
                });
        return areas.get(0).id - 1;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showRequireNetworkDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("訊息通知")
                .setMessage("就醫指南需要網路才能運行，請按確認鍵至手機設定畫面，開啟網路連結，謝謝！")
                .setNegativeButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .create()
                .show();
    }
}
