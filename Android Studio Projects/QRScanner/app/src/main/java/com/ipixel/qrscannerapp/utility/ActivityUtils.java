package com.ipixel.qrscannerapp.utility;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by iPixel Inc on 17/09/2020.
 *
 * Developed by Aryan Kabir
 *
 */
public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }


}
