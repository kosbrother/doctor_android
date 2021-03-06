package kosbrother.com.doctorguide.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kosbrother.com.doctorguide.R;
import kosbrother.com.doctorguide.entity.Area;

import static kosbrother.com.doctorguide.Util.SphericalUtil.computeDistanceBetween;

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

}
